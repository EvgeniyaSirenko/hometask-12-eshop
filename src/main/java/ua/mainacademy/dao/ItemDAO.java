package ua.mainacademy.dao;

import ua.mainacademy.model.Item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import static java.util.Objects.isNull;

public class ItemDAO {
	private static final Logger LOG = Logger.getLogger(ItemDAO.class.getName());
	
	public static Optional<Item> findItemById(Integer id) {
		String sql = "" +
				"SELECT * " +
				"FROM items " +
				"WHERE id = ? ";
		try (
				Connection connection = ConnectionToDB.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql)
		) {
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				Item item = Item.builder()
						.id(resultSet.getInt("id"))
						.name(resultSet.getString("name"))
						.itemCode(resultSet.getString("item_code"))
						.price(resultSet.getInt("price"))
						.initPrice(resultSet.getInt("init_price"))
						.build();
				return Optional.of(item);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}
	
	public static Item create(Item item) {
		String sql = "" +
				"INSERT INTO items (name, item_code, price, init_price)" +
				"VALUES (?, ?, ?, ?)";
		String sequenceSql = "" +
				"SELECT currval('items_id_seq')";
		try (
				Connection connection = ConnectionToDB.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql);
				PreparedStatement sequenceStatement = connection.prepareStatement(sequenceSql)
		) {
			preparedStatement.setString(1, item.getName());
			preparedStatement.setString(2, item.getItemCode());
			preparedStatement.setInt(3, item.getPrice());
			preparedStatement.setInt(4, item.getInitPrice());
			preparedStatement.executeUpdate();
			ResultSet resultSet = sequenceStatement.executeQuery();
			Integer id = null;
			while (resultSet.next()) {
				id = resultSet.getInt(1);
			}
			return Item.builder()
					.id(id)
					.name(item.getName())
					.itemCode(item.getItemCode())
					.price(item.getPrice())
					.initPrice(item.getInitPrice())
					.build();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		throw new RuntimeException(String.format("Item with code %d was not created", item.getItemCode()));
		
	}
	
	public static List<Item> findItemByName(String name) {
		if (isNull(name)) {
			throw new RuntimeException("name is null, finding is impossible");
		}
		List<Item> result = new ArrayList<>();
		String sql = "" +
				"SELECT * FROM items " +
				"WHERE name=? ";
		try (
				Connection connection = ConnectionToDB.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql);
		) {
			preparedStatement.setString(1, name);
			ResultSet resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				Item item = Item.builder()
						.id(resultSet.getInt("id"))
						.name(resultSet.getString("name"))
						.itemCode(resultSet.getString("item_code"))
						.price(resultSet.getInt("price"))
						.initPrice(resultSet.getInt("init_price"))
						.build();
				result.add(item);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static Item update(Item item) {
		if (isNull(item.getId())) {
			throw new RuntimeException("id is null, updating is impossible");
		}
		String sql = "" +
				"UPDATE items " +
				"SET name=?, item_code=?, price=?, init_price=? " +
				"WHERE id=? ";
		try (
				Connection connection = ConnectionToDB.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql);
		) {
			preparedStatement.setString(1, item.getName());
			preparedStatement.setString(2, item.getItemCode());
			preparedStatement.setInt(3, item.getPrice());
			preparedStatement.setInt(4, item.getInitPrice());
			preparedStatement.setInt(5, item.getId());
			preparedStatement.executeUpdate();
			return item;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		throw new RuntimeException(String.format("Item with id %d was not updated", item.getId()));
	}
	
	public static void delete(Integer id) {
		if (isNull(id)) {
			throw new RuntimeException("id is null, deleting is impossible");
		}
		String sql = "DELETE " +
				"FROM items " +
				"WHERE id = ?";
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