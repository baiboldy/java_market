package kz.mandarin.shopping_center.service;

import kz.mandarin.shopping_center.entity.User;
import kz.mandarin.shopping_center.enums.Role;
import kz.mandarin.shopping_center.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;


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

	public void updateToSeller(String username) {
		User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
		user.setRole(Role.ROLE_SELLER);
		userRepository.save(user);
	}

}
