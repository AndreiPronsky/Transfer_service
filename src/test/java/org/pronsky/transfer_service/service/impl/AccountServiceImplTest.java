package org.pronsky.transfer_service.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.pronsky.transfer_service.data.entity.Account;
import org.pronsky.transfer_service.data.entity.User;
import org.pronsky.transfer_service.data.repository.AccountRepository;
import org.pronsky.transfer_service.service.dto.request.TransferRequestDto;
import org.pronsky.transfer_service.service.exception.TransferException;
import org.pronsky.transfer_service.service.security.UserSecurityService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private UserSecurityService userSecurityService;

    @InjectMocks
    private AccountServiceImpl accountService;

    private User currentUser;
    private Account senderAccount;
    private Account recipientAccount;

    @BeforeEach
    void setUp() {
        User senderUser = User.builder()
                .id(1L)
                .dateOfBirth(LocalDate.of(1992, 11, 4))
                .name("Andrei")
                .password("password")
                .build();
        User recipientUser = User.builder()
                .id(2L)
                .dateOfBirth(LocalDate.of(2008, 11, 4))
                .name("Valera")
                .password("password")
                .build();
        currentUser = User.builder()
                .id(1L)
                .build();
        senderAccount = Account.builder()
                .id(1L)
                .user(senderUser)
                .actualBalance(BigDecimal.valueOf(100))
                .build();
        recipientAccount = Account.builder()
                .id(2L)
                .user(recipientUser)
                .actualBalance(new BigDecimal("50.00"))
                .build();
    }

    @Test
    void shouldTransferMoneySuccessfully() {
        TransferRequestDto dto = TransferRequestDto.builder()
                .senderId(1L)
                .recipientId(2L)
                .amount(new BigDecimal("25.00"))
                .build();

        when(userSecurityService.getCurrentUser()).thenReturn(currentUser);
        when(accountRepository.findByUserForUpdate(1L)).thenReturn(Optional.of(senderAccount));
        when(accountRepository.findByUserForUpdate(2L)).thenReturn(Optional.of(recipientAccount));

        accountService.performTransfer(dto);

        assertEquals(new BigDecimal("75.00"), senderAccount.getActualBalance());
        assertEquals(new BigDecimal("75.00"), recipientAccount.getActualBalance());
    }

    @Test
    void shouldThrowWhenSenderAndRecipientAreSame() {

        TransferRequestDto dto = TransferRequestDto.builder()
                .senderId(1L)
                .recipientId(1L)
                .amount(new BigDecimal("25.00"))
                .build();

        when(userSecurityService.getCurrentUser()).thenReturn(currentUser);
        TransferException ex = assertThrows(TransferException.class, () -> accountService.performTransfer(dto));
        assertEquals("Same Sender and Recipient Account", ex.getMessage());
    }

    @Test
    void shouldThrowWhenSenderNotFound() {
        TransferRequestDto dto = TransferRequestDto.builder()
                .senderId(1L)
                .recipientId(2L)
                .amount(new BigDecimal("25.00"))
                .build();
        when(userSecurityService.getCurrentUser()).thenReturn(currentUser);
        when(accountRepository.findByUserForUpdate(1L)).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> accountService.performTransfer(dto));
        assertEquals("Account 1 not found", ex.getMessage());
    }

    @Test
    void shouldThrowWhenRecipientNotFound() {
        TransferRequestDto dto = TransferRequestDto.builder()
                .senderId(1L)
                .recipientId(2L)
                .amount(new BigDecimal("25.00"))
                .build();
        when(userSecurityService.getCurrentUser()).thenReturn(currentUser);
        when(accountRepository.findByUserForUpdate(1L)).thenReturn(Optional.of(senderAccount));
        when(accountRepository.findByUserForUpdate(2L)).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> accountService.performTransfer(dto));
        assertEquals("Account 2 not found", ex.getMessage());
    }

    @Test
    void shouldThrowWhenInsufficientBalance() {
        TransferRequestDto dto = TransferRequestDto.builder()
                .senderId(1L)
                .recipientId(2L)
                .amount(new BigDecimal("200.00"))
                .build();
        when(userSecurityService.getCurrentUser()).thenReturn(currentUser);
        when(accountRepository.findByUserForUpdate(1L)).thenReturn(Optional.of(senderAccount));

        TransferException transferException = assertThrows(
                TransferException.class, () -> accountService.performTransfer(dto));
        assertEquals("Insufficient Balance on account 1", transferException.getMessage());
    }
}
