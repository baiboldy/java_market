package kz.mandarin.shopping_center.service;

import kz.mandarin.shopping_center.dto.ReviewCreateRequest;
import kz.mandarin.shopping_center.entity.Product;
import kz.mandarin.shopping_center.entity.Review;
import kz.mandarin.shopping_center.entity.User;
import kz.mandarin.shopping_center.repository.ProductRepository;
import kz.mandarin.shopping_center.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {
	private final ProductService productService;
	private final UserService userService;
	private final OrderService orderService;
	private final ReviewRepository reviewRepository;
	private final ProductRepository productRepository;

	public Review createReview(Long userId, ReviewCreateRequest request) {
		User user = userService.getUserById(userId);
		Product product = productService.getProductById(request.getProductId());

		if (!orderService.hasPurchasedProduct(userId, product.getId())) {
			throw new RuntimeException("Order has been purchased");
		}

		if (reviewRepository.existsByAuthorAndProduct(user, product)) {
			throw new RuntimeException("Review already exists");
		}

		Review review = new Review();
		review.setAuthor(user);
		review.setProduct(product);
		review.setRating(request.getRating());
		review.setComment(request.getComment());
		Review savedReview = reviewRepository.save(review);
		updateProductRating(product.getId());
		return savedReview;
	}

	private void updateProductRating(Long productId) {
		Double averageRating = reviewRepository.findAverageRatingByProductId(productId);
		Product product = productService.getProductById(productId);
		product.setRating(averageRating);
		productRepository.save(product);
	}

}
