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
		Item testItem = Item.builder()
				.name("testName")
				.itemCode("111111")
				.price(11)
				.initPrice(11)
				.build();
		Item savedItem = ItemDAO.create(testItem);
		items.add(savedItem);
		User testUser = User.builder()
				.login("testLogin")
				.password("testPassword")
				.firstName("testFirstName")
				.lastName("testLastName")
				.build();
		User savedUser = UserDAO.create(testUser);
		users.add(savedUser);
		Cart testCart = Cart.builder()
				.userId(savedUser.getId())
				.creationTime(1L)
				.status(Cart.Status.OPEN)
				.build();
		Cart savedCart = CartDAO.create(testCart);
		carts.add(savedCart);
		Order testOrder = Order.builder()
				.itemId(savedItem.getId())
				.amount(1)
				.cartId(savedCart.getId())
				.build();
		Order savedOrder = OrderDAO.create(testOrder);
		orders.add(savedOrder);
		Optional<Order> foundOrder = OrderDAO.findOrderById(savedOrder.getId());
		assertTrue(foundOrder.isPresent());
	}
	
	@Test
	void create() {
		Item testItem = Item.builder()
				.name("testName")
				.itemCode("111111")
				.price(11)
				.initPrice(11)
				.build();
		Item savedItem = ItemDAO.create(testItem);
		items.add(savedItem);
		User testUser = User.builder()
				.login("testLogin")
				.password("testPassword")
				.firstName("testFirstName")
				.lastName("testLastName")
				.build();
		User savedUser = UserDAO.create(testUser);
		users.add(savedUser);
		Cart testCart = Cart.builder()
				.userId(savedUser.getId())
				.creationTime(1L)
				.status(Cart.Status.OPEN)
				.build();
		Cart savedCart = CartDAO.create(testCart);
		carts.add(savedCart);
		Order testOrder = Order.builder()
				.itemId(savedItem.getId())
				.amount(1)
				.cartId(savedCart.getId())
				.build();
		Order createdOrder = OrderDAO.create(testOrder);
		orders.add(createdOrder);
		assertNotNull(createdOrder.getId());
		assertEquals(testOrder.getCartId(), createdOrder.getCartId());
	}
	
	@Test
	void update() {
		Item testItem = Item.builder()
				.name("testName")
				.itemCode("111111")
				.price(11)
				.initPrice(11)
				.build();
		Item savedItem = ItemDAO.create(testItem);
		items.add(savedItem);
		User testUser = User.builder()
				.login("testLogin")
				.password("testPassword")
				.firstName("testFirstName")
				.lastName("testLastName")
				.build();
		User savedUser = UserDAO.create(testUser);
		users.add(savedUser);
		Cart testCart = Cart.builder()
				.userId(savedUser.getId())
				.creationTime(1L)
				.status(Cart.Status.OPEN)
				.build();
		Cart savedCart = CartDAO.create(testCart);
		carts.add(savedCart);
		Order testOrder = Order.builder()
				.itemId(savedItem.getId())
				.amount(1)
				.cartId(savedCart.getId())
				.build();
		Order savedOrder = OrderDAO.create(testOrder);
		orders.add(savedOrder);
		Item updateItem = Item.builder()
				.name("updateName")
				.itemCode("222222")
				.price(22)
				.initPrice(22)
				.build();
		Item readyForUpdatedItem = ItemDAO.create(updateItem);
		items.add(readyForUpdatedItem);
		User updateUser = User.builder()
				.login("updateLogin")
				.password("updatePassword")
				.firstName("updateFirstName")
				.lastName("updateLastName")
				.build();
		User readyForUpdatedUser = UserDAO.create(updateUser);
		users.add(readyForUpdatedUser);
		Cart readyForUpdateCart = Cart.builder()
				.id(savedCart.getId())
				.userId(readyForUpdatedUser.getId())
				.creationTime(2L)
				.status(Cart.Status.CLOSED)
				.build();
		Cart updatedCart = CartDAO.update(readyForUpdateCart);
		Order readyForUpdate = Order.builder()
				.id(savedOrder.getId())
				.itemId(readyForUpdatedItem.getId())
				.amount(2)
				.cartId(updatedCart.getId())
				.build();
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
		Item testItem = Item.builder()
				.name("testName")
				.itemCode("111111")
				.price(11)
				.initPrice(11)
				.build();
		Item savedItem = ItemDAO.create(testItem);
		items.add(savedItem);
		User testUser = User.builder()
				.login("testLogin")
				.password("testPassword")
				.firstName("testFirstName")
				.lastName("testLastName")
				.build();
		User savedUser = UserDAO.create(testUser);
		users.add(savedUser);
		Cart testCart = Cart.builder()
				.userId(savedUser.getId())
				.creationTime(1L)
				.status(Cart.Status.OPEN)
				.build();
		Cart savedCart = CartDAO.create(testCart);
		carts.add(savedCart);
		Order testOrder = Order.builder()
				.itemId(savedItem.getId())
				.amount(1)
				.cartId(savedCart.getId())
				.build();
		Order savedOrder = OrderDAO.create(testOrder);
		Optional<Order> foundOrder = OrderDAO.findOrderById(savedOrder.getId());
		assertTrue(foundOrder.isPresent());
		OrderDAO.delete(savedOrder.getId());
		foundOrder = OrderDAO.findOrderById(savedOrder.getId());
		assertFalse(foundOrder.isPresent());
	}
}