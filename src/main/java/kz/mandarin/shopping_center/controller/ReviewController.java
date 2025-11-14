package kz.mandarin.shopping_center.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kz.mandarin.shopping_center.dto.ReviewCreateRequest;
import kz.mandarin.shopping_center.entity.User;
import kz.mandarin.shopping_center.service.ReviewService;
import kz.mandarin.shopping_center.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
@Tag(name = "Review", description = "Управление отзывами")
public class ReviewController {
	private final ReviewService reviewService;
	private final UserService userService;

	@Operation(summary = "Создать отзыв на полученный товар")
	@PostMapping("/create-review")
	public ResponseEntity<?> createReview(@RequestBody @Valid ReviewCreateRequest request, @AuthenticationPrincipal User userDetails) {
		return ResponseEntity.ok(reviewService.createReview(userDetails.getId(), request));
	}
}
