package kz.mandarin.shopping_center.service;

import jakarta.transaction.Transactional;
import kz.mandarin.shopping_center.dto.OrderCreateRequest;
import kz.mandarin.shopping_center.entity.*;
import kz.mandarin.shopping_center.enums.OrderStatus;
import kz.mandarin.shopping_center.enums.ProductStatus;
import kz.mandarin.shopping_center.repository.OrderItemRepository;
import kz.mandarin.shopping_center.repository.OrderRepository;
import kz.mandarin.shopping_center.repository.ProductRepository;
import kz.mandarin.shopping_center.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
	private final OrderRepository orderRepository;
	private final OrderItemRepository orderItemRepository;
	private final UserRepository userRepository;
	private final CartService cartService;
	private final ProductRepository productRepository;

	public Order createOrderFromCart(Long userId, OrderCreateRequest request) {
		User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user not found"));
		Cart cart = cartService.getCartByUser(userId);

		if (cart.getCartItems().isEmpty()) {
			throw new RuntimeException("Cart is empty");
		}
		Order order = new Order();
		order.setCustomer(user);
		order.setShippingAddress(request.getShippingAddress());
		order.setStatus(OrderStatus.PENDING);
		order.setOrderDate(LocalDateTime.now());

		Order savedOrder = orderRepository.save(order);

		BigDecimal totalAmount = BigDecimal.ZERO;
		for (CartItem cartItem : cart.getCartItems()) {
			Product product = cartItem.getProduct();
			if (product.getStockQuantity() < cartItem.getQuantity()) {
				throw new RuntimeException("product quantity less than stock quantity");
			}
			product.setStockQuantity(product.getStockQuantity() - cartItem.getQuantity());
			if (product.getStockQuantity() == 0) {
				product.setStatus(ProductStatus.OUT_OF_STOCK);
			}
			productRepository.save(product);

			OrderItem orderItem = new OrderItem();
			orderItem.setOrder(order);
			orderItem.setProduct(product);
			orderItem.setQuantity(cartItem.getQuantity());
			orderItem.setPriceAtPurchase(product.getPrice());
			orderItemRepository.save(orderItem);
			totalAmount = totalAmount.add(product.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
		}
		savedOrder.setTotalAmount(totalAmount);
		orderRepository.save(savedOrder);
		cartService.clearCart(cart.getId());

		return savedOrder;

	}

	public boolean hasPurchasedProduct(Long userId, Long productId) {
		return orderRepository.existsByCustomerIdAndProductIdAndCompletedStatus(userId, productId, OrderStatus.DELIVERED);
	}
}
