package kz.mandarin.shopping_center.dto;

import lombok.Data;

import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

@Data
public class ProductSearchCriteria {
    private Long categoryId;
    private Pageable pageable;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private Long sellerId;
}
