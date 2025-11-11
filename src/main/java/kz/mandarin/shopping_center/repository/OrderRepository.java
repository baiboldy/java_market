package kz.mandarin.shopping_center.repository;

import kz.mandarin.shopping_center.entity.Order;
import kz.mandarin.shopping_center.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
	boolean existsByCustomerIdAndProductIdAndCompletedStatus(Long customerId, Long productId, OrderStatus orderStatus);
}
