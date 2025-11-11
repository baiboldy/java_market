package kz.mandarin.shopping_center.repository;

import kz.mandarin.shopping_center.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
