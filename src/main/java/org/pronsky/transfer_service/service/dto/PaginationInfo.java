package org.pronsky.transfer_service.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaginationInfo {

    private int currentPage;
    private int itemsPerPage;
    private int totalPages;
    private long totalItems;
    private boolean isFirst;
    private boolean isLast;
    private boolean isEmpty;
}
