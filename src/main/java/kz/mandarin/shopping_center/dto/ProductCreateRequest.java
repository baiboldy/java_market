package kz.mandarin.shopping_center.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.awt.print.Pageable;
import java.math.BigDecimal;

@Data
public class ProductCreateRequest {
    @NotBlank(message = "Наименование товара не может быть пустыми")
    private String title;
    private String description;
    private BigDecimal price;
    private Integer stockQuantity;
    private Long categoryId;
}
