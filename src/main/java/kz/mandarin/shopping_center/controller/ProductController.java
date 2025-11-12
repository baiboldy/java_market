package kz.mandarin.shopping_center.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kz.mandarin.shopping_center.dto.ProductSearchCriteria;
import kz.mandarin.shopping_center.dto.product.ProductPageableResponseDto;
import kz.mandarin.shopping_center.entity.Product;
import kz.mandarin.shopping_center.service.ProductService;
import kz.mandarin.shopping_center.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
@Tag(name = "Products", description = "Поиск всех товаров")
public class ProductController {
	private final ProductService productService;

	@PostMapping("/search")
	public ResponseEntity<?> searchProducts(@RequestBody @Valid ProductSearchCriteria criteria, Pageable pageable) {
		Page<Product> products = productService.searchProducts(criteria, pageable);
		return ResponseEntity.ok(ProductPageableResponseDto.productToDto(products));
	}
}
