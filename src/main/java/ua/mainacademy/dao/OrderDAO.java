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
			return Order.builder()
					.id(id)
					.itemId(order.getItemId())
					.amount(order.getAmount())
					.cartId(order.getCartId())
					.build();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		throw new RuntimeException(String.format("Order with cartId %d was not created", order.getCartId()));
	}
	
	public static Order update (Order order) {
		if(isNull(order.getId())) {
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
			preparedStatement.setInt(5, order.getId());
			preparedStatement.executeUpdate();
			return Order.builder()
					.id(order.getId())
					.itemId(order.getItemId())
					.amount(order.getAmount())
					.cartId(order.getCartId())
					.build();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		throw new RuntimeException(String.format("Order with id %d was not updated", order.getId()));
	}
	
	public static Optional<Order> findOrderById (Integer id) {
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
				Order order = Order.builder()
						.id(resultSet.getInt("id"))
						.itemId(resultSet.getInt("item_id"))
						.amount(resultSet.getInt("amount"))
						.cartId(resultSet.getInt("cart_id"))
						.build();
				return Optional.of(order);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}
	
	public static void delete (Integer id) {
		if(isNull(id)) {
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
