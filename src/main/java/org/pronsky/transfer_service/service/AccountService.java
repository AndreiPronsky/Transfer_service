package org.pronsky.transfer_service.service;

import org.pronsky.transfer_service.service.dto.request.TransferRequestDto;

public interface AccountService {

    void performTransfer(TransferRequestDto transferRequestDto);
}
