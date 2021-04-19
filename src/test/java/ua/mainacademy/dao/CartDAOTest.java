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
		Optional<Cart> foundCart = CartDAO.findCartById(savedCart.getId());
		assertTrue(foundCart.isPresent());
	}
	
	@Test
	void create() {
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
		Cart createdCart = CartDAO.create(testCart);
		carts.add(createdCart);
		assertNotNull(createdCart.getId());
		assertEquals(testCart.getCreationTime(), createdCart.getCreationTime());
	}
	
	@Test
	void update() {
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
		User updateUser = new User();
		updateUser.setLogin("updateLogin");
		updateUser.setPassword("updatePassword");
		updateUser.setFirstName("updateFirstName");
		updateUser.setLastName("updateLastName");
		User readyForUpdatedUser = UserDAO.create(updateUser);
		users.add(readyForUpdatedUser);
		Cart readyForUpdate = new Cart();
		readyForUpdate.setId(savedCart.getId());
		readyForUpdate.setUserId(readyForUpdatedUser.getId());
		readyForUpdate.setCreationTime(2L);
		readyForUpdate.setStatus(Cart.Status.CLOSED);
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
		Optional<Cart> foundCart = CartDAO.findCartById(savedCart.getId());
		assertTrue(foundCart.isPresent());
		CartDAO.delete(savedCart.getId());
		foundCart = CartDAO.findCartById(savedCart.getId());
		assertFalse(foundCart.isPresent());
	}
}