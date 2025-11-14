package kz.mandarin.shopping_center.service;

import kz.mandarin.shopping_center.dto.user.ProfileEditDto;
import kz.mandarin.shopping_center.dto.user.UserResponseDto;
import kz.mandarin.shopping_center.entity.User;
import kz.mandarin.shopping_center.enums.Role;
import kz.mandarin.shopping_center.mapper.UserMapper;
import kz.mandarin.shopping_center.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;


	public void createUser(User userRequest) {
		if (userRepository.existsByEmail(userRequest.getEmail())) {
			throw new RuntimeException("Emial уже существует: " + userRequest.getEmail());
		}
		userRepository.save(userRequest);
	}

	public UserDetailsService userDetailsService() {
		return this::getByUsername;
	}

	public User getByUsername(String username) {
		return userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));

	}

	public User getUserById(Long id) {
		return userRepository.findById(id).orElseThrow(() -> new RuntimeException("Пользователь не найден"));
	}

	public void updateToSeller(Long userId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
		user.setRole(Role.ROLE_SELLER);
		userRepository.save(user);
	}

	public UserResponseDto updateProfileInfo(ProfileEditDto profileEditDto, Long id) {
		User currentUser = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
		profileEditDto.getFirstName().ifPresent(currentUser::setFirstName);
		profileEditDto.getLastName().ifPresent(currentUser::setLastName);
		profileEditDto.getEmail().ifPresent(currentUser::setEmail);
		return UserMapper.toUserResponseDto(userRepository.save(currentUser));
	}

}
