package kz.mandarin.shopping_center.dto.user;

import kz.mandarin.shopping_center.enums.Role;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserResponseDto {
	private Long id;
	private String username;
	private String firstName;
	private String lastName;
	private String email;
	private Role role;
}
