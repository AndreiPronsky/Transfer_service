package org.pronsky.transfer_service.service;

import org.pronsky.transfer_service.service.dto.request.TransferRequestDto;
import org.pronsky.transfer_service.service.dto.response.TransferResponseDto;
import org.springframework.stereotype.Service;

@Service
public interface AccountService {

    TransferResponseDto performTransfer(TransferRequestDto transferRequestDto);
}
