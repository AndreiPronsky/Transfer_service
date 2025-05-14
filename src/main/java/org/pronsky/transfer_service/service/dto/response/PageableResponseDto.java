package org.pronsky.transfer_service.service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.pronsky.transfer_service.service.dto.PaginationInfo;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageableResponseDto<T> {

    private List<T> content;
    private PaginationInfo paginationInfo;
}
