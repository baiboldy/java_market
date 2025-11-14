package kz.mandarin.shopping_center.dto;

import lombok.Data;

@Data
public class AddToCartRequestDto {
    private Long productId;
	private Integer stockQuantity;
}
