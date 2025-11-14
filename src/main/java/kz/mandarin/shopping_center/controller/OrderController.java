package kz.mandarin.shopping_center.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kz.mandarin.shopping_center.dto.OrderCreateRequest;
import kz.mandarin.shopping_center.entity.User;
import kz.mandarin.shopping_center.repository.OrderRepository;
import kz.mandarin.shopping_center.repository.UserRepository;
import kz.mandarin.shopping_center.service.OrderService;
import kz.mandarin.shopping_center.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
@Tag(name = "Order", description = "Управление заказами")
public class OrderController {
	private final OrderService orderService;

	@PostMapping("/create-order-from-cart")
	@Operation(summary = "Создать заказ из корзины")
	public ResponseEntity<?> createOrderFromCart(@RequestBody OrderCreateRequest request, @AuthenticationPrincipal User userDetails) {
		try {
			return ResponseEntity.ok(orderService.createOrderFromCart(userDetails.getId(), request));
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		}
	}
}
