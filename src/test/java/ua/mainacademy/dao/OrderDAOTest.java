package ua.mainacademy.dao;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ua.mainacademy.model.Cart;
import ua.mainacademy.model.Item;
import ua.mainacademy.model.Order;
import ua.mainacademy.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class OrderDAOTest {
	
	private static List<Item> items;
	private static List<User> users;
	private static List<Cart> carts;
	private static List<Order> orders;
	
	@BeforeAll
	public static void setData() {
		items = new ArrayList<>();
		users = new ArrayList<>();
		carts = new ArrayList<>();
		orders = new ArrayList<>();
	}
	
	@AfterAll
	public static void deleteData() {
		orders.forEach(order -> OrderDAO.delete(order.getId()));
		carts.forEach(cart -> CartDAO.delete(cart.getId()));
		items.forEach(item -> ItemDAO.delete(item.getId()));
		users.forEach(user -> UserDAO.delete(user.getId()));
	}
	
	@Test
	void findOrderById() {
		Item testItem = new Item();
		testItem.setName("testName");
		testItem.setItemCode("11111");
		testItem.setPrice(5);
		testItem.setInitPrice(11);
		testItem.setId(100);
		Item savedItem = ItemDAO.create(testItem);
		items.add(savedItem);
		User testUser = new User();
		testUser.setLogin("testLogin");
		testUser.setPassword("testPassword");
		testUser.setFirstName("testFirstName");
		testUser.setLastName("testLastName");
		User savedUser = UserDAO.create(testUser);
		users.add(savedUser);
		Cart testCart = new Cart();
		testCart.setUserId(savedUser.getId());
		testCart.setCreationTime(1L);
		testCart.setStatus(Cart.Status.OPEN);
		Cart savedCart = CartDAO.create(testCart);
		carts.add(savedCart);
		Order testOrder = new Order();
		testOrder.setItemId(savedItem.getId());
		testOrder.setAmount(1);
		testOrder.setCartId(savedCart.getId());
		Order savedOrder = OrderDAO.create(testOrder);
		orders.add(savedOrder);
		Optional<Order> foundOrder = OrderDAO.findOrderById(savedOrder.getId());
		assertTrue(foundOrder.isPresent());
	}
	
	@Test
	void create() {
		Item testItem = new Item();
		testItem.setName("testName");
		testItem.setItemCode("11111");
		testItem.setPrice(5);
		testItem.setInitPrice(11);
		testItem.setId(100);
		Item savedItem = ItemDAO.create(testItem);
		items.add(savedItem);
		User testUser = new User();
		testUser.setLogin("testLogin");
		testUser.setPassword("testPassword");
		testUser.setFirstName("testFirstName");
		testUser.setLastName("testLastName");
		User savedUser = UserDAO.create(testUser);
		users.add(savedUser);
		Cart testCart = new Cart();
		testCart.setUserId(savedUser.getId());
		testCart.setCreationTime(1L);
		testCart.setStatus(Cart.Status.OPEN);
		Cart savedCart = CartDAO.create(testCart);
		carts.add(savedCart);
		Order testOrder = new Order();
		testOrder.setItemId(savedItem.getId());
		testOrder.setAmount(1);
		testOrder.setCartId(savedCart.getId());
		Order createdOrder = OrderDAO.create(testOrder);
		orders.add(createdOrder);
		assertNotNull(createdOrder.getId());
		assertEquals(testOrder.getCartId(), createdOrder.getCartId());
	}
	
	@Test
	void update() {
		Item testItem = new Item();
		testItem.setName("testName");
		testItem.setItemCode("11111");
		testItem.setPrice(5);
		testItem.setInitPrice(11);
		testItem.setId(100);
		Item savedItem = ItemDAO.create(testItem);
		items.add(savedItem);
		User testUser = new User();
		testUser.setLogin("testLogin");
		testUser.setPassword("testPassword");
		testUser.setFirstName("testFirstName");
		testUser.setLastName("testLastName");
		User savedUser = UserDAO.create(testUser);
		users.add(savedUser);
		Cart testCart = new Cart();
		testCart.setUserId(savedUser.getId());
		testCart.setCreationTime(1L);
		testCart.setStatus(Cart.Status.OPEN);
		Cart savedCart = CartDAO.create(testCart);
		carts.add(savedCart);
		Order testOrder = new Order();
		testOrder.setItemId(savedItem.getId());
		testOrder.setAmount(1);
		testOrder.setCartId(savedCart.getId());
		Order savedOrder = OrderDAO.create(testOrder);
		orders.add(savedOrder);
		Item updateItem = new Item();
		updateItem.setName("updatedName");
		updateItem.setItemCode("222222");
		updateItem.setPrice(22);
		updateItem.setInitPrice(22);
		Item readyForUpdatedItem = ItemDAO.create(updateItem);
		items.add(readyForUpdatedItem);
		User updateUser = new User();
		updateUser.setLogin("updateLogin");
		updateUser.setPassword("updatePassword");
		updateUser.setFirstName("updateFirstName");
		updateUser.setLastName("updateLastName");
		User readyForUpdatedUser = UserDAO.create(updateUser);
		users.add(readyForUpdatedUser);
		Cart readyForUpdateCart = new Cart();
		readyForUpdateCart.setId(savedCart.getId());
		readyForUpdateCart.setUserId(readyForUpdatedUser.getId());
		readyForUpdateCart.setCreationTime(2L);
		readyForUpdateCart.setStatus(Cart.Status.CLOSED);
		Cart updatedCart = CartDAO.update(readyForUpdateCart);
		Order readyForUpdate = new Order();
		readyForUpdate.setId(savedOrder.getId());
		readyForUpdate.setItemId(readyForUpdatedItem.getId());
		readyForUpdate.setAmount(2);
		readyForUpdate.setCartId(updatedCart.getId());
		Order updatedOrder = OrderDAO.update(readyForUpdate);
		Optional<Order> foundOrder = OrderDAO.findOrderById(updatedOrder.getId());
		if (foundOrder.isPresent()) {
			assertNotEquals(savedOrder.getAmount(), foundOrder.get().getAmount());
			assertEquals(savedOrder.getId(), foundOrder.get().getId());
		} else {
			fail("Updated cart was not found");
		}
	}
	
	@Test
	void delete() {
		Item testItem = new Item();
		testItem.setName("testName");
		testItem.setItemCode("11111");
		testItem.setPrice(5);
		testItem.setInitPrice(11);
		testItem.setId(100);
		Item savedItem = ItemDAO.create(testItem);
		items.add(savedItem);
		User testUser = new User();
		testUser.setLogin("testLogin");
		testUser.setPassword("testPassword");
		testUser.setFirstName("testFirstName");
		testUser.setLastName("testLastName");
		User savedUser = UserDAO.create(testUser);
		users.add(savedUser);
		Cart testCart = new Cart();
		testCart.setUserId(savedUser.getId());
		testCart.setCreationTime(1L);
		testCart.setStatus(Cart.Status.OPEN);
		Cart savedCart = CartDAO.create(testCart);
		carts.add(savedCart);
		Order testOrder = new Order();
		testOrder.setItemId(savedItem.getId());
		testOrder.setAmount(1);
		testOrder.setCartId(savedCart.getId());
		Order savedOrder = OrderDAO.create(testOrder);
		Optional<Order> foundOrder = OrderDAO.findOrderById(savedOrder.getId());
		assertTrue(foundOrder.isPresent());
		OrderDAO.delete(savedOrder.getId());
		foundOrder = OrderDAO.findOrderById(savedOrder.getId());
		assertFalse(foundOrder.isPresent());
	}
}