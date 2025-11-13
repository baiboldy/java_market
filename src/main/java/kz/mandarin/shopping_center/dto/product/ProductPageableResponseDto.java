package kz.mandarin.shopping_center.dto.product;

import kz.mandarin.shopping_center.entity.Product;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Builder
@Data
public class ProductPageableResponseDto {
	private List<ProductDto> products;
	private int page;
	private int size;
	private int totalPages;
	private Long totalElements;

	public static ProductPageableResponseDto productToDto(Page<ProductDto> products) {
		return ProductPageableResponseDto.builder()
				.page(products.getPageable().getPageNumber())
				.size(products.getPageable().getPageSize())
				.products(products.getContent())
				.totalElements(products.getTotalElements())
				.totalPages(products.getTotalPages())
				.build();
	}
}
