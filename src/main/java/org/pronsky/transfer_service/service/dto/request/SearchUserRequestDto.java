package org.pronsky.transfer_service.service.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SearchUserRequestDto {

    @Pattern(regexp = "\\d{2}\\.\\d{2}\\.\\d{4}", message = "Date of birth must be in the format dd.MM.yyyy")
    private String dateOfBirth;

    @Pattern(regexp = "7\\d{10}", message = "Phone number must start with '7' and contain 11 digits total")
    private String phone;

    @Size(max = 500, message = "Name must be less than or equal to 500 characters")
    private String name;

    @Email(message = "Email must be valid")
    @Size(max = 200, message = "Email must be less than or equal to 200 characters")
    private String email;
}
