package org.pronsky.transfer_service.service.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AddEmailToUserRequestDto {

    private Long userId;

    @NotBlank(message = "Email must not be blank")
    @Email(message = "Email must be valid")
    @Size(max = 200, message = "Email must be less than or equal to 200 characters")
    private String email;
}
