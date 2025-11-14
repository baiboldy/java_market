package kz.mandarin.shopping_center.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import kz.mandarin.shopping_center.dto.ProductCreateRequest;
import kz.mandarin.shopping_center.dto.ProductSearchCriteria;
import kz.mandarin.shopping_center.dto.product.ProductDto;
import kz.mandarin.shopping_center.dto.product.ProductPageableResponseDto;
import kz.mandarin.shopping_center.entity.User;
import kz.mandarin.shopping_center.mapper.ProductMapper;
import kz.mandarin.shopping_center.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
@Tag(name = "Products", description = "Управление товаром")
public class ProductController {
	private final ProductService productService;
	private final ProductMapper productMapper;


	@GetMapping("/{id}")
	@Operation(summary = "Получить товар по идентификатору")
	public ResponseEntity<?> getProductById(@PathVariable Long id) {
		try {
			return ResponseEntity.ok(productMapper.toDto(productService.getProductById(id)));
		} catch (Exception ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
		}
	}

	@PreAuthorize("hasRole('SELLER')")
	@PostMapping("/search")
	@Operation(summary = "Поиск товара с фильтром и пагинацией")
	public ResponseEntity<?> searchProducts(@RequestBody @Valid ProductSearchCriteria criteria, Pageable pageable) {
		Page<ProductDto> products = productService.searchProducts(criteria, pageable).map(productMapper::toDto);
		return ResponseEntity.ok(ProductPageableResponseDto.productToDto(products));
	}

	@PreAuthorize("hasRole('SELLER')")
	@PostMapping("/create-product")
	@Operation(summary = "Создать товар")
	public ResponseEntity<?> createProduct(@RequestBody @Valid ProductCreateRequest productRequest, @AuthenticationPrincipal User userDetails) {
		return ResponseEntity.ok(productService.createProduct(productRequest, userDetails.getId()));
	}

	@PreAuthorize("hasRole('SELLER')")
	@PutMapping("/update-stock")
	@Operation(summary = "Обновить количество товара")
	public ResponseEntity<?> updateStock(@RequestBody Long productId, @RequestBody Integer newStock, @AuthenticationPrincipal User userDetails) {
		return ResponseEntity.ok(productService.updateStock(productId, newStock, userDetails.getId()));
	}

}
