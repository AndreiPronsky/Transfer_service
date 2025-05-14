package org.pronsky.transfer_service.service.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateUserRequestDto {

    @NotNull(message = "User for update ID must not be null")
    @Positive(message = "User for update ID must be a positive number")
    private Long id;

    @NotBlank(message = "Phone number must not be blank")
    @Pattern(regexp = "7\\d{10}", message = "Phone number must start with '7' and contain 11 digits total")
    private String phoneNumber;

    @NotBlank(message = "Email must not be blank")
    @Email(message = "Email must be valid")
    @Size(max = 200, message = "Email must be less than or equal to 200 characters")
    private String email;
}
