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

    private static final String INSUFFICIENT_BALANCE = "Insufficient Balance on account %s";
    private static final String SAME_SENDER_AND_RECIPIENT_ACCOUNT = "Same Sender and Recipient Account";
    private static final String ACCOUNT_NOT_FOUND = "Account %s not found";
    private static final String SUCCESSFUL_TRANSFER = "Transfer in the amount of %s from %s to %s successfully handled";

    private final AccountRepository accountRepository;

    @Override
    @Transactional
    public void performTransfer(TransferRequestDto transferRequestDto) {
        checkSenderAndRecipientAccountIdentity(transferRequestDto);

        Account senderAccount = accountRepository.findByIdForUpdate(transferRequestDto.getSenderAccountId())
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format(ACCOUNT_NOT_FOUND, transferRequestDto.getSenderAccountId()))
                );

        checkBalance(senderAccount, transferRequestDto.getAmount());

        Account recipientAccount = accountRepository.findByIdForUpdate(transferRequestDto.getRecipientAccountId())
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format(ACCOUNT_NOT_FOUND, transferRequestDto.getRecipientAccountId()))
                );

        senderAccount.setActualBalance(senderAccount.getActualBalance().subtract(transferRequestDto.getAmount()));
        recipientAccount.setActualBalance(recipientAccount.getActualBalance().add(transferRequestDto.getAmount()));

        log.info(String.format(SUCCESSFUL_TRANSFER,
                transferRequestDto.getAmount(),
                transferRequestDto.getSenderAccountId(),
                transferRequestDto.getRecipientAccountId())
        );
    }

    private void checkBalance(Account senderAccount, BigDecimal amount) {
        BigDecimal balance = senderAccount.getActualBalance();
        if (balance.compareTo(amount) < 0) {
            log.warn(String.format(INSUFFICIENT_BALANCE, senderAccount.getId()));
            throw new TransferException(String.format(INSUFFICIENT_BALANCE, senderAccount.getId()));
        }
    }

    private void checkSenderAndRecipientAccountIdentity(TransferRequestDto transferRequestDto) {
        Long senderAccountId = transferRequestDto.getSenderAccountId();
        Long recipientAccountId = transferRequestDto.getRecipientAccountId();
        if (senderAccountId.equals(recipientAccountId)) {
            log.warn(SAME_SENDER_AND_RECIPIENT_ACCOUNT);
            throw new TransferException(SAME_SENDER_AND_RECIPIENT_ACCOUNT);
        }
    }
}
