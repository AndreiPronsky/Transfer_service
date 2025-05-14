package org.pronsky.transfer_service.service.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateUserRequestDto {

    @NotBlank(message = "Name must not be blank")
    @Size(max = 500, message = "Name must be less than or equal to 500 characters")
    private String name;

    @NotBlank(message = "Date of birth must not be blank")
    @Pattern(regexp = "\\d{2}\\.\\d{2}\\.\\d{4}", message = "Date of birth must be in the format dd.MM.yyyy")
    private String dateOfBirth;

    @NotBlank(message = "Password must not be blank")
    @Size(min = 8, max = 500, message = "Password must be between 8 and 500 characters")
    private String password;

    @NotBlank(message = "Phone number must not be blank")
    @Pattern(regexp = "7\\d{10}", message = "Phone number must start with '7' and contain 11 digits total")
    private String phoneNumber;

    @NotBlank(message = "Email must not be blank")
    @Email(message = "Email must be valid")
    @Size(max = 200, message = "Email must be less than or equal to 200 characters")
    private String email;
}
