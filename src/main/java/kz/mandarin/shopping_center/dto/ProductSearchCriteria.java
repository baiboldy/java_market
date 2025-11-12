package kz.mandarin.shopping_center.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

@Data
@Schema(description = "Запрос на поиск товаров")
public class ProductSearchCriteria {
	@Schema(description = "Идентификатор категории", example = "1")
	private Long categoryId;
	@Schema(description = "Минимальная цена товара", example = "5")
	private BigDecimal minPrice;
	@Schema(description = "Максимальная цена товара", example = "1000")
	private BigDecimal maxPrice;
	@Schema(description = "Идентификатор продавца", example = "2")
	private Long sellerId;
}
