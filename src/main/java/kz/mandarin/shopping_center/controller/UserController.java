package kz.mandarin.shopping_center.controller;

import kz.mandarin.shopping_center.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;

	@PostMapping("/update-to-seller")
	public ResponseEntity<String> updateToSeller(@AuthenticationPrincipal UserDetails userDetails) {
		userService.updateToSeller(userDetails.getUsername());
		return ResponseEntity.ok("Updated");
	}
}
