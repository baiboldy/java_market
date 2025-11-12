package kz.mandarin.shopping_center.repository;

import kz.mandarin.shopping_center.entity.Order;
import kz.mandarin.shopping_center.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<Order, Long> {
	// Простой вариант - проверка существования
	@Query("""
        SELECT COUNT(oi) > 0 
        FROM Order o 
        JOIN o.orderItems oi 
        WHERE o.customer.id = :userId 
        AND oi.product.id = :productId 
        AND o.status = :status
    """)
	boolean existsByCustomerIdAndProductIdAndCompletedStatus(
			@Param("userId") Long userId,
			@Param("productId") Long productId,
			@Param("status") OrderStatus status
	);
}
