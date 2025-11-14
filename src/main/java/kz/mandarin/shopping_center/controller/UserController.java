package kz.mandarin.shopping_center.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kz.mandarin.shopping_center.dto.user.ProfileEditDto;
import kz.mandarin.shopping_center.dto.user.UserResponseDto;
import kz.mandarin.shopping_center.entity.User;
import kz.mandarin.shopping_center.repository.UserRepository;
import kz.mandarin.shopping_center.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "User", description = "Управление пользователями")
public class UserController {
	private final UserService userService;


	@Operation(summary = "Обновить роль пользователя на Продавца")
	@PostMapping("/update-to-seller")
	public ResponseEntity<String> updateToSeller(@AuthenticationPrincipal User userDetails) {
		userService.updateToSeller(userDetails.getId());
		return ResponseEntity.ok("Updated");
	}

	@PreAuthorize("hasAnyRole('SELLER', 'BUYER', 'ADMIN')")
	@PatchMapping("/edit-personal-data")
	@Operation(summary = "Изменение данных пользователя")
	public ResponseEntity<?> editProfileInfo(@RequestBody @Valid ProfileEditDto profileEditDto,
											 @AuthenticationPrincipal User userDetails) {
		try {
			UserResponseDto userResponseDto = userService.updateProfileInfo(profileEditDto, userDetails.getId());
			return ResponseEntity.ok(userResponseDto);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		}
	}


}
