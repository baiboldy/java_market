package kz.mandarin.shopping_center.mapper;

import kz.mandarin.shopping_center.dto.user.UserResponseDto;
import kz.mandarin.shopping_center.entity.User;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
public class UserMapper {
	public static UserResponseDto toUserResponseDto(User user) {
		return UserResponseDto.builder()
				.id(user.getId())
				.firstName(user.getFirstName())
				.lastName(user.getLastName())
				.email(user.getEmail())
				.role(user.getRole())
				.build();
	}
}
