package org.pronsky.transfer_service.service.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Data transfer object to store data for phone number update")
public class UpdatePhoneRequestDto {

    @NotNull(message = "User ID must not be null")
    @Positive(message = "User ID must be a positive number")
    private Long userId;

    @NotNull(message = "Phone ID must not be null")
    @Positive(message = "Phone ID must be a positive number")
    private Long phoneId;

    @NotBlank(message = "New phone number must not be blank")
    @Pattern(regexp = "7\\d{10}", message = "New phone number must start with '7' and contain 11 digits total")
    private String newPhoneNumber;

}
