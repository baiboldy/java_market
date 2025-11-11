package kz.mandarin.shopping_center.repository;

import kz.mandarin.shopping_center.entity.Product;
import kz.mandarin.shopping_center.entity.Review;
import kz.mandarin.shopping_center.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
	boolean existsByAuthorAndProduct(User author, Product product);
	Double findAverageRatingByProductId(Long productId);
}
