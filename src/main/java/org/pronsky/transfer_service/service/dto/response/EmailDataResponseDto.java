package org.pronsky.transfer_service.service.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.pronsky.transfer_service.service.dto.EmailDataDto;

import java.util.Set;

@Getter
@Builder
public class EmailDataResponseDto {

    private Long userId;
    private Set<EmailDataDto> emails;
}
