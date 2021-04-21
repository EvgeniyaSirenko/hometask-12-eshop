package ua.mainacademy;

import ua.mainacademy.dao.ItemDAO;
import ua.mainacademy.model.Item;

import java.util.logging.Logger;

public class AppRunner {
	public static final Logger LOG = Logger.getLogger(AppRunner.class.getName());
	
	public static void main(String[] args) {
		
		Item item = new Item();
		item.setName("someGood4");
		item.setItemCode("56RD");
		item.setPrice(47);
		item.setInitPrice(48);
		Item savedItem = ItemDAO.create(item);
		LOG.info(String.format("New Item is: %s", savedItem));
		LOG.info(String.format("New Item by id is: %s", ItemDAO.findItemById(savedItem.getId())));
		
		ItemDAO.delete(savedItem.getId());
		LOG.info(String.format("Item with id %d is deleted", savedItem.getId()));
	}
}
