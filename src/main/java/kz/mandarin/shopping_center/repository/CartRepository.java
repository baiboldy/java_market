package kz.mandarin.shopping_center.repository;

import kz.mandarin.shopping_center.entity.Cart;
import kz.mandarin.shopping_center.entity.CartItem;
import kz.mandarin.shopping_center.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
	Optional<Cart> findByUser(User user);
	Optional<Cart> findByUserId(Long userId);
}
