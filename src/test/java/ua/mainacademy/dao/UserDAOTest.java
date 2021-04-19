package ua.mainacademy.dao;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ua.mainacademy.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class UserDAOTest {
	
	private static List<User> users;
	
	@BeforeAll
	public static void setData() {
		users = new ArrayList<>();
	}
	
	@AfterAll
	public static void deleteData() {
		users.forEach(user -> UserDAO.delete(user.getId()));
	}
	
	
	@Test
	void findUserById() {
		User testUser = new User();
		testUser.setLogin("testLogin");
		testUser.setPassword("testPassword");
		testUser.setFirstName("testFirstName");
		testUser.setLastName("testLastName");
		User savedUser = UserDAO.create(testUser);
		users.add(savedUser);
		Optional<User> foundUser = UserDAO.findUserById(savedUser.getId());
		assertTrue(foundUser.isPresent());
	}
	
	@Test
	void create() {
		User testUser = new User();
		testUser.setLogin("3testLogin");
		testUser.setPassword("testPassword");
		testUser.setFirstName("testFirstName");
		testUser.setLastName("testLastName");
		User createdUser = UserDAO.create(testUser);
		users.add(createdUser);
		assertNotNull(createdUser.getId());
		assertEquals(testUser.getFirstName(), createdUser.getFirstName());
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
		User readyForUpdate = new User();
		readyForUpdate.setId(savedUser.getId());
		readyForUpdate.setLogin("newLogin");
		readyForUpdate.setPassword("newPassword");
		readyForUpdate.setFirstName("newFirstName");
		readyForUpdate.setLastName("newLastName");
		User updatedUser = UserDAO.update(readyForUpdate);
		users.add(updatedUser);
		Optional<User> foundUser = UserDAO.findUserById(updatedUser.getId());
		if (foundUser.isPresent()) {
			assertNotEquals(savedUser.getFirstName(), foundUser.get().getFirstName());
			assertEquals(savedUser.getId(), foundUser.get().getId());
		} else {
			fail("Updated item was not found");
		}
	}
	
	@Test
	void delete() {
		User testUser = new User();
		testUser.setLogin("testLogin");
		testUser.setPassword("testPassword");
		testUser.setFirstName("testFirstName");
		testUser.setLastName("testLastName");
		testUser.setId(100);
		User savedUser = UserDAO.create(testUser);
		Optional<User> foundUser = UserDAO.findUserById(savedUser.getId());
		assertTrue(foundUser.isPresent());
		UserDAO.delete(savedUser.getId());
		foundUser = UserDAO.findUserById(savedUser.getId());
		assertFalse(foundUser.isPresent());
	}
}