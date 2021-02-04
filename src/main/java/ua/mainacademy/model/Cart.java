package ua.mainacademy.model;

import lombok.*;

import java.util.Date;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
	
	private Integer id;
	private Integer userId;
	private Long creationTime;
	private Status status;
	
	@Getter
	public enum Status {
		OPEN,
		CLOSED
	}
	
}
