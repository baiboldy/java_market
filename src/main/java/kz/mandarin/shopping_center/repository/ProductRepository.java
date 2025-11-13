package kz.mandarin.shopping_center.repository;

import kz.mandarin.shopping_center.entity.Product;
import kz.mandarin.shopping_center.enums.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
	Optional<Product> findByIdAndSellerUsername(Long id, String username);
    Optional<Product> findByIdAndSellerId(Long Id, Long sellerId);
    Optional<Product> findByIdAndStatus(Long Id, ProductStatus status);
}
