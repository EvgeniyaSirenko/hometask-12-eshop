package ua.mainacademy.dao;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ua.mainacademy.model.Cart;
import ua.mainacademy.model.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class ItemDAOTest {
	
	private static List<Item> items;
	
	@BeforeAll
	public static void setData() {
		items = new ArrayList<>();
	}
	
	@AfterAll
	public static void deleteData() {
		items.forEach(item -> ItemDAO.delete(item.getId()));
	}
	
	
	@Test
	void findItemById() {
		Item testItem = new Item();
		testItem.setName("testName");
		testItem.setItemCode("11111");
		testItem.setPrice(5);
		testItem.setInitPrice(11);
		//testItem.setId(100);
		Item savedItem = ItemDAO.create(testItem);
		items.add(savedItem);
		Optional<Item> foundItem = ItemDAO.findItemById(savedItem.getId());
		assertTrue(foundItem.isPresent());
	}
	
	@Test
	void create() {
		Item testItem = new Item();
		testItem.setName("testName");
		testItem.setItemCode("11111");
		testItem.setPrice(5);
		testItem.setInitPrice(11);
		testItem.setId(100);
		Item createdItem = ItemDAO.create(testItem);
		items.add(createdItem);
		assertNotNull(createdItem.getId());
		assertEquals(testItem.getName(), createdItem.getName());
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
		Item readyForUpdate = new Item();
		readyForUpdate.setId(savedItem.getId());
		readyForUpdate.setName("updatedName");
		readyForUpdate.setItemCode("111222");
		readyForUpdate.setPrice(6);
		readyForUpdate.setInitPrice(11);
		Item updatedItem = ItemDAO.update(readyForUpdate);
		items.add(updatedItem);
		Optional<Item> foundItem = ItemDAO.findItemById(updatedItem.getId());
		if (foundItem.isPresent()) {
			assertNotEquals(savedItem.getName(), foundItem.get().getName());
			assertEquals(savedItem.getId(), foundItem.get().getId());
		} else {
			fail("Updated item was not found");
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
		Optional<Item> foundItem = ItemDAO.findItemById(savedItem.getId());
		assertTrue(foundItem.isPresent());
		ItemDAO.delete(savedItem.getId());
		foundItem = ItemDAO.findItemById(savedItem.getId());
		assertFalse(foundItem.isPresent());
	}
}