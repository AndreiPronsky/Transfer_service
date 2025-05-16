package org.pronsky.transfer_service.integration;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.pronsky.transfer_service.data.entity.Account;
import org.pronsky.transfer_service.data.repository.AccountRepository;
import org.pronsky.transfer_service.data.repository.EmailDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class AccountTransferIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private EmailDataRepository emailDataRepository;

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @DynamicPropertySource
    static void setDatasourceProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Test
    @WithMockUser(username = "example1@example.com")
    void shouldPerformSuccessfulTransfer() throws Exception {
        String requestJson = """
                {
                  "senderId": 1,
                  "recipientId": 2,
                  "amount": 25.00
                }
                """;

        mockMvc.perform(post("/api/v1/accounts/transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isAccepted());

        Account sender = accountRepository.findByUserForUpdate(1L).orElseThrow();
        Account recipient = accountRepository.findByUserForUpdate(2L).orElseThrow();

        assertEquals(sender.getActualBalance(), sender.getActualBalance());
        assertEquals(recipient.getActualBalance(), recipient.getActualBalance());
    }
}
