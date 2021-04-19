package ua.mainacademy.model;

public class Cart {
	
	private Integer id;
	private Integer userId;
	private Long creationTime;
	private Status status;
	
	public enum Status {
		OPEN,
		CLOSED
	}
	
	public Cart() {
	}
	
	public Cart(Integer id, Integer userId, Long creationTime, Status status) {
		this.id = id;
		this.userId = userId;
		this.creationTime = creationTime;
		this.status = status;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getUserId() {
		return userId;
	}
	
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	public Long getCreationTime() {
		return creationTime;
	}
	
	public void setCreationTime(Long creationTime) {
		this.creationTime = creationTime;
	}
	
	public Status getStatus() {
		return status;
	}
	
	public void setStatus(Status status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		return "Cart { " +
				"id= " + id +
				", userId= " + userId +
				", creationTime= " + creationTime +
				", status= " + status +
				"}";
	}
}
