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
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public Product createProduct(ProductCreateRequest productRequest, String username) {
        User seller = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));

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

    public Product updateStock(Long productId, Integer newStock, String username) {
        Product product = productRepository.findByIdAndSellerUsername(productId, username).orElseThrow(() -> new RuntimeException("Product not found"));
        product.setStockQuantity(newStock);
        if (newStock == 0) {
            product.setStatus(ProductStatus.OUT_OF_STOCK);
        }
        return productRepository.save(product);

    }

    public Page<Product> searchProducts(ProductSearchCriteria criteria, Pageable pageable) {
        Specification<Product> specification = Specification.unrestricted();

        if (criteria.getCategoryId() != null)
            specification = specification.and(ProductSpecification.byCategory(criteria.getCategoryId()));

        if (criteria.getMinPrice() != null)
            specification = specification.and(ProductSpecification.priceGreaterThan(criteria.getMinPrice()));

        if (criteria.getMaxPrice() != null)
            specification = specification.and(ProductSpecification.priceLessThan(criteria.getMaxPrice()));

        if (criteria.getSellerId() != null)
            specification = specification.and(ProductSpecification.bySeller(criteria.getSellerId()));

        return productRepository.findAll(specification, pageable);
    }

	public Product getProductById(Long productId) throws  RuntimeException {
		return productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
	}

}
