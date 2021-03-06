package ua.mainacademy.dao;

import ua.mainacademy.model.Cart;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static java.util.Objects.isNull;

public class CartDAO {
	
	public static Cart create(Cart cart) {
		String sql = "" +
				"INSERT INTO carts(creation_time, user_id, status) " +
				"VALUES(?,?,?)";
		String sequenceSql = "" +
				"SELECT currval('carts_id_seq')";
		
		try (
				Connection connection = ConnectionToDB.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql);
				PreparedStatement sequenceStatement = connection.prepareStatement(sequenceSql)
		) {
			preparedStatement.setLong(1, cart.getCreationTime());
			preparedStatement.setInt(2, cart.getUserId());
			preparedStatement.setInt(3, cart.getStatus().ordinal());
			preparedStatement.executeUpdate();
			ResultSet resultSet = sequenceStatement.executeQuery();
			Integer id = null;
			while (resultSet.next()) {
				id = resultSet.getInt(1);
			}
			Cart savedCart = new Cart();
			savedCart.setId(id);
			savedCart.setCreationTime(cart.getCreationTime());
			savedCart.setUserId(cart.getUserId());
			savedCart.setStatus(cart.getStatus());
			return savedCart;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		throw new RuntimeException(String.format("Cart with userId %d was not created", cart.getUserId()));
	}
	
	public static Cart update(Cart cart) {
		if (isNull(cart.getId())) {
			throw new RuntimeException("id is null, update is impossible");
		}
		String sql = "" +
				"UPDATE carts " +
				"SET creation_time=?, user_id=?, status=? " +
				"WHERE id=?";
		try (
				Connection connection = ConnectionToDB.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql);
		) {
			preparedStatement.setLong(1, cart.getCreationTime());
			preparedStatement.setInt(2, cart.getUserId());
			preparedStatement.setInt(3, cart.getStatus().ordinal());
			preparedStatement.setInt(4, cart.getId());
			preparedStatement.executeUpdate();
			Cart savedCart = new Cart();
			savedCart.setId(cart.getId());
			savedCart.setCreationTime(cart.getCreationTime());
			savedCart.setUserId(cart.getUserId());
			savedCart.setStatus(cart.getStatus());
			return savedCart;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		throw new RuntimeException(String.format("Cart with id %d was not updated", cart.getId()));
	}
	
	public static Optional<Cart> findCartById(Integer id) {
		String sql = "" +
				"SELECT * FROM carts " +
				"WHERE id=?";
		try (
				Connection connection = ConnectionToDB.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql);
		) {
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				Cart cart = new Cart();
				cart.setId(resultSet.getInt("id"));
				cart.setCreationTime(resultSet.getLong("creation_time"));
				cart.setUserId(resultSet.getInt("user_id"));
				cart.setStatus(Cart.Status.values()[resultSet.getInt("status")]);
				return Optional.of(cart);
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
				"FROM carts " +
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
