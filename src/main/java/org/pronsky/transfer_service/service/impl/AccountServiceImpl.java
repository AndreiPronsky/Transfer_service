package org.pronsky.transfer_service.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pronsky.transfer_service.data.entity.Account;
import org.pronsky.transfer_service.data.repository.AccountRepository;
import org.pronsky.transfer_service.service.AccountService;
import org.pronsky.transfer_service.service.dto.request.TransferRequestDto;
import org.pronsky.transfer_service.service.exception.TransferException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountServiceImpl implements AccountService {

    private static final String INSUFFICIENT_BALANCE = "Insufficient Balance on account {}";
    private static final String SAME_SENDER_AND_RECIPIENT_ACCOUNT = "Same Sender and Recipient Account";
    private static final String ACCOUNT_NOT_FOUND = "Account %s not found";
    private static final String SUCCESSFUL_TRANSFER = "Transfer in the amount of {} from {} to {} successfully handled";

    private final AccountRepository accountRepository;

    @Override
    @Transactional
    public void performTransfer(TransferRequestDto transferRequestDto) {
        checkSenderAndRecipientAccountIdentity(transferRequestDto);

        Account senderAccount = accountRepository.findByUserForUpdate(transferRequestDto.getSenderId())
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format(ACCOUNT_NOT_FOUND, transferRequestDto.getSenderId()))
                );

        checkBalance(senderAccount, transferRequestDto.getAmount());

        Account recipientAccount = accountRepository.findByUserForUpdate(transferRequestDto.getRecipientId())
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format(ACCOUNT_NOT_FOUND, transferRequestDto.getRecipientId()))
                );

        senderAccount.setActualBalance(senderAccount.getActualBalance().subtract(transferRequestDto.getAmount()));
        recipientAccount.setActualBalance(recipientAccount.getActualBalance().add(transferRequestDto.getAmount()));

        log.info(SUCCESSFUL_TRANSFER,
                transferRequestDto.getAmount(),
                transferRequestDto.getSenderId(),
                transferRequestDto.getRecipientId()
        );
    }

    private void checkBalance(Account senderAccount, BigDecimal amount) {
        BigDecimal balance = senderAccount.getActualBalance();
        if (balance.compareTo(amount) < 0) {
            String message = String.format("Insufficient Balance on account %d", senderAccount.getId());
            log.warn(message);
            throw new TransferException(message);
        }
    }

    private void checkSenderAndRecipientAccountIdentity(TransferRequestDto transferRequestDto) {
        Long senderAccountId = transferRequestDto.getSenderId();
        Long recipientAccountId = transferRequestDto.getRecipientId();
        if (senderAccountId.equals(recipientAccountId)) {
            log.warn(SAME_SENDER_AND_RECIPIENT_ACCOUNT);
            throw new TransferException(SAME_SENDER_AND_RECIPIENT_ACCOUNT);
        }
    }
}
