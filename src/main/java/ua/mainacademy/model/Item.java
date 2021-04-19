package ua.mainacademy.model;

public class Item {
	
	private Integer id;
	private String itemCode;
	private String name;
	private Integer price;
	private Integer initPrice;
	
	public Item(Integer id, String itemCode, String name, Integer price, Integer initPrice) {
		this.id = id;
		this.itemCode = itemCode;
		this.name = name;
		this.price = price;
		this.initPrice = initPrice;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getItemCode() {
		return itemCode;
	}
	
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getPrice() {
		return price;
	}
	
	public void setPrice(Integer price) {
		this.price = price;
	}
	
	public Integer getInitPrice() {
		return initPrice;
	}
	
	public void setInitPrice(Integer initPrice) {
		this.initPrice = initPrice;
	}
	
	public Item() {
	}
}
