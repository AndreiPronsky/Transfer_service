package org.pronsky.transfer_service.service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SingleUserResponseDto {

    private Long id;
    private String name;
    private LocalDate dateOfBirth;
}
