package org.pronsky.transfer_service.service.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PageableUserResponseDto {
private List<SingleUserResponseDto> users;
}
