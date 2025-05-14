package org.pronsky.transfer_service.service.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.pronsky.transfer_service.service.dto.PhoneDataDto;

import java.util.Set;

@Getter
@Builder
public class PhoneDataResponseDto {

    private Long userId;
    private Set<PhoneDataDto> phoneNumbers;
}
