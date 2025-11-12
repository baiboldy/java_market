package kz.mandarin.shopping_center.specification;

import kz.mandarin.shopping_center.entity.Product;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class ProductSpecification {
    public static Specification<Product> byCategory(Long categoryId) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.join("category").get("id"), categoryId);
    }

    public static Specification<Product> priceGreaterThan(BigDecimal price) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get("price"), price);
    }

    public static Specification<Product> priceLessThan(BigDecimal price) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get("price"), price);
    }
    public static Specification<Product> bySeller(Long sellerId) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("seller").get("id"), sellerId);
    }
}
