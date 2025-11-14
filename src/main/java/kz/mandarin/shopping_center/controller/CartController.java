package kz.mandarin.shopping_center.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kz.mandarin.shopping_center.dto.AddToCartRequestDto;
import kz.mandarin.shopping_center.entity.User;
import kz.mandarin.shopping_center.service.CartService;
import kz.mandarin.shopping_center.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
@Tag(name = "Carts", description = "Управление корзиной")
public class CartController {
	private final CartService cartService;
	private final UserService userService;

	@PreAuthorize("hasRole('BUYER')")
	@PostMapping("/add-to-cart")
	@Operation(summary = "Добавить в корзину")
	public ResponseEntity<?> addToCart(@RequestBody @Valid AddToCartRequestDto requestDto, @AuthenticationPrincipal User userDetails) {
		try {
			return ResponseEntity.ok(cartService.addToCart(userDetails.getId(), requestDto));
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		}
	}

	@PreAuthorize("hasRole('BUYER')")
	@DeleteMapping("{cartItemId}")
	@Operation(summary = "Удалить товар из корзины")
	public ResponseEntity<?> removeFromCart(@PathVariable Long cartItemId, @AuthenticationPrincipal User userDetails) {
		try {
			cartService.removeFromCart(userDetails.getId(), cartItemId);
			return ResponseEntity.ok().build();
		} catch (Exception ex) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), ex);
		}
	}
}
