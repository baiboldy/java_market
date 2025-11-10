package kz.mandarin.shopping_center.repository;

import kz.mandarin.shopping_center.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
