package org.pronsky.transfer_service.service.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "Data transfer object to store data to add phone number to the specified user")
public class AddPhoneToUserRequestDto {

    @NotNull(message = "User ID must not be null")
    @Positive(message = "User ID must be a positive number")
    private Long userId;

    @NotBlank(message = "Phone number must not be blank")
    @Pattern(regexp = "7\\d{10}", message = "Phone number must start with '7' and contain 11 digits total")
    private String phoneNumber;
}
