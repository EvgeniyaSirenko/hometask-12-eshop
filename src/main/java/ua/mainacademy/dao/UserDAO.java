package ua.mainacademy.dao;

import ua.mainacademy.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static java.util.Objects.isNull;

public class UserDAO {
	
	public static User create(User user) {
		String sql = "" +
				"INSERT INTO users(login, password, first_name, last_name) " +
				"VALUES(?,?,?,?)";
		String sequenceSql = "" +
				"SELECT currval('users_id_seq')";
		
		try (
				Connection connection = ConnectionToDB.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql);
				PreparedStatement sequenceStatement = connection.prepareStatement(sequenceSql)
		) {
			preparedStatement.setString(1, user.getLogin());
			preparedStatement.setString(2, user.getPassword());
			preparedStatement.setString(3, user.getFirstName());
			preparedStatement.setString(4, user.getLastName());
			preparedStatement.executeUpdate();
			ResultSet resultSet = sequenceStatement.executeQuery();
			Integer id = null;
			while (resultSet.next()) {
				id = resultSet.getInt(1);
			}
			User savedUser = new User();
			savedUser.setId(id);
			savedUser.setLogin(user.getLogin());
			savedUser.setPassword(user.getPassword());
			savedUser.setFirstName(user.getFirstName());
			savedUser.setLastName(user.getLastName());
			return savedUser;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		throw new RuntimeException(String
				.format("User with login %s and password %s was not created", user.getLogin(), user.getPassword()));
	}
	
	public static User update(User user) {
		if (isNull(user.getId())) {
			throw new RuntimeException("id is null, update is impossible");
		}
		String sql = "" +
				"UPDATE users " +
				"SET login=?, password=?, first_name=?, last_name=? " +
				"WHERE id=?";
		try (
				Connection connection = ConnectionToDB.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql);
		) {
			preparedStatement.setString(1, user.getLogin());
			preparedStatement.setString(2, user.getPassword());
			preparedStatement.setString(3, user.getFirstName());
			preparedStatement.setString(4, user.getLastName());
			preparedStatement.setInt(5, user.getId());
			preparedStatement.executeUpdate();
			User savedUser = new User();
			savedUser.setId(user.getId());
			savedUser.setLogin(user.getLogin());
			savedUser.setPassword(user.getPassword());
			savedUser.setFirstName(user.getFirstName());
			savedUser.setLastName(user.getLastName());
			return savedUser;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		throw new RuntimeException(String.format("User with id %d was not updated", user.getId()));
	}
	
	public static Optional<User> findUserById(Integer id) {
		String sql = "" +
				"SELECT * FROM users " +
				"WHERE id=?";
		try (
				Connection connection = ConnectionToDB.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql);
		) {
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				User user = new User();
				user.setId(resultSet.getInt("id"));
				user.setLogin(resultSet.getString("login"));
				user.setPassword(resultSet.getString("password"));
				user.setFirstName(resultSet.getString("first_name"));
				user.setLastName(resultSet.getString("last_name"));
				return Optional.of(user);
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
				"FROM users " +
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
