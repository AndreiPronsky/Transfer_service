package org.pronsky.transfer_service.service.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateEmailRequestDto {

    @NotNull(message = "User ID must not be null")
    @Positive(message = "User ID must be a positive number")
    private Long userId;

    @NotNull(message = "Email ID must not be null")
    @Positive(message = "Email ID must be a positive number")
    private Long emailId;

    @NotBlank(message = "New email must not be blank")
    @Email(message = "New email must be valid")
    @Size(max = 200, message = "New email must be less than or equal to 200 characters")
    private String newEmail;
}
