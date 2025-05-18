package org.pronsky.transfer_service.service.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Response with pagination option")
public class PageableResponseDto<T> {

    @Schema(description = "List of objects", example = "data[]")
    private List<T> content;

    @Schema(description = "Pagination information", example = """
            "paginationInfo": {
                    "currentPage": 1,
                    "itemsPerPage": 3,
                    "totalPages": 1,
                    "totalItems": 3
                }
            """)
    private PaginationInfo paginationInfo;
}
