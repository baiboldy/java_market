package kz.mandarin.shopping_center.entity;

import jakarta.persistence.*;
import kz.mandarin.shopping_center.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @CreatedDate
    private LocalDateTime createdDate;
    @UpdateTimestamp
    private LocalDateTime updatedDate;
    @ManyToOne
    private User customer;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;

    private BigDecimal totalAmount;
    private OrderStatus status; // PENDING, PAID, SHIPPED, DELIVERED, CANCELLED
    private String shippingAddress;
    private LocalDateTime orderDate;
}
