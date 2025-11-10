package kz.mandarin.shopping_center.service;

import jakarta.transaction.Transactional;
import kz.mandarin.shopping_center.dto.ProductCreateRequest;
import kz.mandarin.shopping_center.dto.ProductSearchCriteria;
import kz.mandarin.shopping_center.entity.Product;
import kz.mandarin.shopping_center.entity.User;
import kz.mandarin.shopping_center.enums.ProductStatus;
import kz.mandarin.shopping_center.repository.CategoryRepository;
import kz.mandarin.shopping_center.repository.ProductRepository;
import kz.mandarin.shopping_center.repository.UserRepository;
import kz.mandarin.shopping_center.specification.ProductSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    @Transactional
    public Product createProduct(ProductCreateRequest productRequest, Long sellerId) {
        User seller = userRepository.findById(sellerId).orElseThrow(() -> new RuntimeException("User not found"));

        Product product = new Product();
        product.setTitle(productRequest.getTitle());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setStockQuantity(productRequest.getStockQuantity());
        product.setSeller(seller);
        product.setCategory(categoryRepository.findById(productRequest.getCategoryId()).orElseThrow(() -> new RuntimeException("Category not found")));
        product.setStatus(ProductStatus.AVAILABLE);
        return productRepository.save(product);
    }

    public Product updateStock(Long productId, Integer newStock, Long sellerId) {
        Product product = productRepository.findByIdAndSellerId(productId, sellerId).orElseThrow(() -> new RuntimeException("Product not found"));
        product.setStockQuantity(newStock);
        if (newStock == 0) {
            product.setStatus(ProductStatus.OUT_OF_STOCK);
        }
        return productRepository.save(product);

    }

    public Page<Product> searchProducts(ProductSearchCriteria criteria) {
        Specification<Product> specification = Specification.unrestricted();

        if (criteria.getCategoryId() != null)
            specification = specification.and(ProductSpecification.byCategory(criteria.getCategoryId()));

        if (criteria.getMinPrice() != null)
            specification = specification.and(ProductSpecification.priceGreaterThan(criteria.getMinPrice()));

        if (criteria.getMaxPrice() != null)
            specification = specification.and(ProductSpecification.priceLessThan(criteria.getMaxPrice()));

        if (criteria.getSellerId() != null)
            specification = specification.and(ProductSpecification.bySeller(criteria.getSellerId()));

        return productRepository.findAll(specification, criteria.getPageable());
    }

}
