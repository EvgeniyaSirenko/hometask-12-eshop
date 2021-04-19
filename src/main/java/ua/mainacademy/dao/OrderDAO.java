package ua.mainacademy.dao;

import ua.mainacademy.model.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static java.util.Objects.isNull;

public class OrderDAO {
	
	public static Order create(Order order) {
		String sql = "" +
				"INSERT INTO orders(item_id, amount, cart_id) " +
				"VALUES(?,?,?)";
		String sequenceSql = "" +
				"SELECT currval('orders_id_seq')";
		
		try (
				Connection connection = ConnectionToDB.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql);
				PreparedStatement sequenceStatement = connection.prepareStatement(sequenceSql)
		) {
			preparedStatement.setInt(1, order.getItemId());
			preparedStatement.setInt(2, order.getAmount());
			preparedStatement.setInt(3, order.getCartId());
			preparedStatement.executeUpdate();
			ResultSet resultSet = sequenceStatement.executeQuery();
			Integer id = null;
			while (resultSet.next()) {
				id = resultSet.getInt(1);
			}
			Order savedOrder = new Order();
			savedOrder.setId(id);
			savedOrder.setItemId(order.getItemId());
			savedOrder.setAmount(order.getAmount());
			savedOrder.setCartId(order.getCartId());
			return savedOrder;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		throw new RuntimeException(String.format("Order with cartId %d was not created", order.getCartId()));
	}
	
	public static Order update(Order order) {
		if (isNull(order.getId())) {
			throw new RuntimeException("id is null, update is impossible");
		}
		String sql = "" +
				"UPDATE orders " +
				"SET item_id=?, amount=?, cart_id=? " +
				"WHERE id=?";
		try (
				Connection connection = ConnectionToDB.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql);
		) {
			preparedStatement.setInt(1, order.getItemId());
			preparedStatement.setInt(2, order.getAmount());
			preparedStatement.setInt(3, order.getCartId());
			preparedStatement.setInt(4, order.getId());
			preparedStatement.executeUpdate();
			Order savedOrder = new Order();
			savedOrder.setId(order.getId());
			savedOrder.setItemId(order.getItemId());
			savedOrder.setAmount(order.getAmount());
			savedOrder.setCartId(order.getCartId());
			return savedOrder;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		throw new RuntimeException(String.format("Order with id %d was not updated", order.getId()));
	}
	
	public static Optional<Order> findOrderById(Integer id) {
		String sql = "" +
				"SELECT * FROM orders " +
				"WHERE id=?";
		try (
				Connection connection = ConnectionToDB.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql);
		) {
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				Order order = new Order();
				order.setId(resultSet.getInt("id"));
				order.setItemId(resultSet.getInt("item_id"));
				order.setAmount(resultSet.getInt("amount"));
				order.setCartId(resultSet.getInt("cart_id"));
				return Optional.of(order);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}
	
	public static void delete(Integer id) {
		if (isNull(id)) {
			throw new RuntimeException("id is null, delete is impossible");
		}
		String sql = "DELETE " +
				"FROM orders " +
				"WHERE id=?";
		try (
				Connection connection = ConnectionToDB.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql);
		) {
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
