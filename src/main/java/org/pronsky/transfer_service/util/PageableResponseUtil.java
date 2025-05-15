package org.pronsky.transfer_service.util;

import org.pronsky.transfer_service.service.dto.PaginationInfo;
import org.pronsky.transfer_service.service.dto.response.PageableResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PageableResponseUtil {

    public <T, R extends PageableResponseDto<T>> R buildPageableResponse(List<T> content, Page<?> page, R response) {
        response.setContent(content);
        response.setPaginationInfo(
                PaginationInfo.builder()
                        .currentPage(page.getNumber() + 1)
                        .itemsPerPage(page.getNumberOfElements())
                        .totalPages(page.getTotalPages())
                        .totalItems(page.getTotalElements())
                        .isFirst(page.isFirst())
                        .isLast(page.isLast())
                        .isEmpty(page.isEmpty())
                        .build());

        return response;
    }
}
