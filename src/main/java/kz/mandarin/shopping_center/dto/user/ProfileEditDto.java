package kz.mandarin.shopping_center.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Optional;

@Data
public class ProfileEditDto {
	@Schema(description = "Имя пользователя", example = "Петр")
	private Optional<String> firstName = Optional.empty();
	@Schema(description = "Фамилия пользователя", example = "Петров")
	private Optional<String> lastName = Optional.empty();
	@Schema(description = "Почта пользователя", example = "petrov.petr@gmail.com")
	private Optional<String> email = Optional.empty();
}
