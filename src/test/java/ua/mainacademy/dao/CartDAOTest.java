package ua.mainacademy.dao;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ua.mainacademy.model.Cart;
import ua.mainacademy.model.User;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class CartDAOTest {
	
	private static List<User> users;
	private static List<Cart> carts;
	
	@BeforeAll
	public static void setData() {
		users = new ArrayList<>();
		carts = new ArrayList<>();
	}
	
	@AfterAll
	public static void deleteData() {
		carts.forEach(cart -> CartDAO.delete(cart.getId()));
		users.forEach(user -> UserDAO.delete(user.getId()));
	}
	
	@Test
	void findCartById() {
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
		Optional<Cart> foundCart = CartDAO.findCartById(savedCart.getId());
		assertTrue(foundCart.isPresent());
	}
	
	@Test
	void create() {
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
		Cart createdCart = CartDAO.create(testCart);
		carts.add(createdCart);
		assertNotNull(createdCart.getId());
		assertEquals(testCart.getCreationTime(), createdCart.getCreationTime());
	}
	
	@Test
	void update() {
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
		User updateUser = User.builder()
				.login("updateLogin")
				.password("updatePassword")
				.firstName("updateFirstName")
				.lastName("updateLastName")
				.build();
		User readyForUpdatedUser = UserDAO.create(updateUser);
		users.add(readyForUpdatedUser);
		Cart readyForUpdate = Cart.builder()
				.id(savedCart.getId())
				.userId(readyForUpdatedUser.getId())
				.creationTime(2L)
				.status(Cart.Status.CLOSED)
				.build();
		Cart updatedCart = CartDAO.update(readyForUpdate);
		Optional<Cart> foundCart = CartDAO.findCartById(updatedCart.getId());
		if (foundCart.isPresent()) {
			assertNotEquals(savedCart.getCreationTime(), foundCart.get().getCreationTime());
			assertEquals(savedCart.getId(), foundCart.get().getId());
		} else {
			fail("Updated cart was not found");
		}
	}
	
	@Test
	void delete() {
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
		Optional<Cart> foundCart = CartDAO.findCartById(savedCart.getId());
		assertTrue(foundCart.isPresent());
		CartDAO.delete(savedCart.getId());
		foundCart = CartDAO.findCartById(savedCart.getId());
		assertFalse(foundCart.isPresent());
	}
}