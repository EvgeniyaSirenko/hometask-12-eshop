package ua.mainacademy.model;

public class Order {
	
	private Integer id;
	private Integer itemId;
	private Integer amount;
	private Integer cartId;
	
	public Order() {
	}
	
	public Order(Integer id, Integer itemId, Integer amount, Integer cartId) {
		this.id = id;
		this.itemId = itemId;
		this.amount = amount;
		this.cartId = cartId;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getItemId() {
		return itemId;
	}
	
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	
	public Integer getAmount() {
		return amount;
	}
	
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	
	public Integer getCartId() {
		return cartId;
	}
	
	public void setCartId(Integer cartId) {
		this.cartId = cartId;
	}
}
