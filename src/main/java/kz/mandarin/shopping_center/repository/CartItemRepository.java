package kz.mandarin.shopping_center.repository;

import kz.mandarin.shopping_center.entity.Cart;
import kz.mandarin.shopping_center.entity.CartItem;
import kz.mandarin.shopping_center.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
	Optional<CartItem> findByCartAndProduct(Cart cart, Product product);
}
