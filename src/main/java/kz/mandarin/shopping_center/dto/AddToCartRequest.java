package kz.mandarin.shopping_center.dto;

import kz.mandarin.shopping_center.enums.ProductStatus;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AddToCartRequest {
    private Long productId;
	private Integer stockQuantity;
}
