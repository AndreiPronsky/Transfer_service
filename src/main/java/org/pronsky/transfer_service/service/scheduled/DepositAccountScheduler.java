package org.pronsky.transfer_service.service.scheduled;

import lombok.RequiredArgsConstructor;
import org.pronsky.transfer_service.data.entity.Account;
import org.pronsky.transfer_service.data.repository.AccountRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Component
@Transactional
@RequiredArgsConstructor
public class DepositAccountScheduler {

    private final AccountRepository accountRepository;

    @Scheduled(fixedRate = 30000)
    public void recalculateBalances() {
        List<Account> accounts = accountRepository.findAll();

        accounts.forEach(account -> {
                    BigDecimal initialBalance = account.getInitialBalance();
                    BigDecimal actualBalance = account.getActualBalance();

                    BigDecimal maxAllowedBalance = initialBalance.multiply(BigDecimal.valueOf(2.07));

                    if (actualBalance.compareTo(maxAllowedBalance) < 0) {
                        BigDecimal renewedBalance = actualBalance.multiply(BigDecimal.valueOf(1.1));
                        account.setActualBalance(renewedBalance);
                        if (actualBalance.compareTo(maxAllowedBalance) > 0) {
                            account.setActualBalance(maxAllowedBalance);
                        }
                    }
                }
        );

    }
}
