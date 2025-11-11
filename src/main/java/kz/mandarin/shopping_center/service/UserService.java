package kz.mandarin.shopping_center.service;

import kz.mandarin.shopping_center.dto.SignUpRequest;
import kz.mandarin.shopping_center.dto.UserRequest;
import kz.mandarin.shopping_center.dto.UserResponse;
import kz.mandarin.shopping_center.entity.User;
import kz.mandarin.shopping_center.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<UserResponse> findAll() {
        return userRepository.findAll().stream().map(this::convertToResponse).collect(Collectors.toList());
    }

    public UserResponse findById(Long id) {
        return convertToResponse(userRepository.findById(id).orElseThrow(() -> new RuntimeException(("Поль"))));
    }

    public User createUser(User userRequest) {
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new RuntimeException("Emial уже существует: " + userRequest.getEmail());
        }
        return userRepository.save(userRequest);
    }

    public UserResponse updateUser(Long id, UserRequest userRequest) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден с id: " + id));

        // Проверка email на уникальность (если изменился)
        if (!user.getEmail().equals(userRequest.getEmail()) &&
                userRepository.existsByEmail(userRequest.getEmail())) {
            throw new RuntimeException("Email уже используется: " + userRequest.getEmail());
        }

        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());

        User updatedUser = userRepository.save(user);
        return convertToResponse(updatedUser);
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("Пользователь не найден с id: " + id);
        }
        userRepository.deleteById(id);
    }

    private UserResponse convertToResponse(User user) {
        return new UserResponse(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail());
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

}
