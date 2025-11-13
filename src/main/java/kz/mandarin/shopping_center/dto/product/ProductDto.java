package kz.mandarin.shopping_center.dto.product;

import jakarta.persistence.*;
import kz.mandarin.shopping_center.entity.Category;
import kz.mandarin.shopping_center.entity.User;
import kz.mandarin.shopping_center.enums.ProductStatus;
import lombok.Builder;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder
public class ProductDto {
	private Long id;
	private String title;
	private String description;
	private BigDecimal price;
	private Integer stockQuantity;
	private ProductStatus status;
	private Long categoryId;
	private Long seller;
	private Double rating;
	private List<String> images;
}
