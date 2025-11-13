package kz.mandarin.shopping_center.mapper;

import kz.mandarin.shopping_center.dto.product.ProductDto;
import kz.mandarin.shopping_center.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
	public ProductDto toDto(Product product) {
		if (product == null)
			return null;
		return ProductDto.builder()
				.id(product.getId())
				.title(product.getTitle())
				.description(product.getDescription())
				.price(product.getPrice())
				.stockQuantity(product.getStockQuantity())
				.status(product.getStatus())
				.categoryId(product.getCategory() != null ? product.getCategory().getId() : null)
				.seller(product.getSeller() != null ? product.getSeller().getId() : null)
				.rating(product.getRating())
				.images(product.getImages())
				.build();

	}
}
