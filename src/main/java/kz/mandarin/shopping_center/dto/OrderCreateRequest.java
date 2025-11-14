package kz.mandarin.shopping_center.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class OrderCreateRequest {

	@Size(min = 5, max = 100, message = "Адрес должен содержать от 5 до 100 символов")
	@Schema(description = "Адрес доставки", example = "ул. Строителей 22, дом 23")
	@NotBlank(message = "Адрес не может быть пустыми")
	private String shippingAddress;
}
