package kz.mandarin.shopping_center.service;

import kz.mandarin.shopping_center.dto.AddToCartRequestDto;
import kz.mandarin.shopping_center.entity.Cart;
import kz.mandarin.shopping_center.entity.CartItem;
import kz.mandarin.shopping_center.entity.Product;
import kz.mandarin.shopping_center.entity.User;
import kz.mandarin.shopping_center.enums.ProductStatus;
import kz.mandarin.shopping_center.repository.CartItemRepository;
import kz.mandarin.shopping_center.repository.CartRepository;
import kz.mandarin.shopping_center.repository.ProductRepository;
import kz.mandarin.shopping_center.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.nio.file.AccessDeniedException;

@Service
@RequiredArgsConstructor
public class CartService {
	private final CartRepository cartRepository;
	private final UserRepository userRepository;
	private final ProductRepository productRepository;
	private final CartItemRepository cartItemRepository;

	public CartItem addToCart(Long userId, AddToCartRequestDto addToCartRequestDto) {
		User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user not found"));
		Product product = productRepository.findByIdAndStatus(addToCartRequestDto.getProductId(), ProductStatus.AVAILABLE).orElseThrow(() -> new RuntimeException("product not found"));
		if (product.getStockQuantity() < addToCartRequestDto.getStockQuantity())
			throw new RuntimeException("Not enough stock");
		Cart cart = cartRepository.findByUser(user).orElseGet(() -> cartRepository.save(Cart.builder().user(user).build()));
		CartItem existingItem = cartItemRepository.findByCartAndProduct(cart, product).orElseThrow(() -> new RuntimeException("item not found"));

		if (existingItem != null) {
			existingItem.setQuantity(existingItem.getQuantity() + addToCartRequestDto.getStockQuantity());
			return cartItemRepository.save(existingItem);
		} else {
			CartItem newItem = new CartItem();
			newItem.setCart(cart);
			newItem.setProduct(product);
			newItem.setQuantity(addToCartRequestDto.getStockQuantity());
			return cartItemRepository.save(newItem);
		}
	}

	public void removeFromCart(Long userId, Long cartItemId) throws AccessDeniedException {
		CartItem item = cartItemRepository.findById(cartItemId).orElseThrow(() -> new RuntimeException("item not found"));
		if (!item.getCart().getUser().getId().equals(userId)) {
			throw new AccessDeniedException("Not your cart");
		}
		cartItemRepository.delete(item);
		this.updateCartTotal(item.getCart().getId());
	}

	private void updateCartTotal(Long cartId) {
		Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("item not found"));
		BigDecimal total = cart.getCartItems().stream().map(this::calculateItemTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
		cart.setTotalPrice(total);
		cartRepository.save(cart);
	}

	private BigDecimal calculateItemTotal(CartItem cartItem) {
		return cartItem.getProduct().getPrice()
				.multiply(BigDecimal.valueOf(cartItem.getQuantity()));
	}

	public Cart getCartByUser(Long userId) {
		return cartRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("Cart not found"));
	}

	public void clearCart(Long cartId) {
		Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("cart not found"));
		cart.getCartItems().clear();
		cartRepository.save(cart);
		cartRepository.deleteById(cartId);
	}

}
