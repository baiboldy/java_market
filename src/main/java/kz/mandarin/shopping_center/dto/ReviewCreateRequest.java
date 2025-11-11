package kz.mandarin.shopping_center.dto;

import lombok.Data;

@Data
public class ReviewCreateRequest {
	private Long productId;
	private Integer rating;
	private String comment;
}
