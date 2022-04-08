package com.anz.accountmanagement;

import com.anz.accountmanagement.controller.AccountController;
import com.anz.accountmanagement.models.Account;
import com.anz.accountmanagement.repository.AccountRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AccountControllerTest {

        private MockMvc mockMvc;

        @Autowired
        private ObjectMapper mapper;

        @MockBean
        private AccountRepository accountRepository;

        private static final DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        private static String BASE_PATH = "http://localhost/accounts";
        private static String TRANSACTIONS_PATH = "/transactions";
        private static final long ID = 1;
        private Account account;

      /*  @Before
        public void setup() {
            mockMvc =
                    MockMvcBuilders.standaloneSetup(new AccountController(accountRepository)).build();
            setupAccount();
        }*/

        private void setupAccount() {
            account = new Account();
            account.setId(ID);
            account.setAccountNumber("123-456-789");
            account.setAccountName("AUDSavings");
            account.setBalanceDate("2012-01-12");
            account.setAccountType("Savings");

        }

        @Test
        public void getReturnsCorrectResponse() throws Exception {
            given(accountRepository.findById(ID)).willReturn(Optional.of(account));
            final ResultActions result = mockMvc.perform(get(BASE_PATH + "/" + ID));
            result.andExpect(status().isOk());
            verifyJson(result);
        }


        private void verifyJson(final ResultActions action) throws Exception {
            action
                    .andExpect(jsonPath("account.id", is(account.getId().intValue())))
                    .andExpect(jsonPath("account.accountNumber", is(account.getAccountNumber())))
                    .andExpect(jsonPath("account.accountName", is(account.getAccountName())))
                    .andExpect(jsonPath("account.accountType", is(account.getAccountType())))
                    .andExpect(jsonPath("links[0].rel", is("account")))
                    .andExpect(jsonPath("links[0].href", is(BASE_PATH)))
                    .andExpect(jsonPath("links[1].rel", is("transactions")))
                    .andExpect(jsonPath("links[1].href", is(BASE_PATH + "/" + ID + TRANSACTIONS_PATH)))
                    .andExpect(jsonPath("links[2].rel", is("self")))
                    .andExpect(jsonPath("links[2].href", is(BASE_PATH + "/" + ID)));
        }

        @Test
        public void allReturnsCorrectResponse() throws Exception {
            given(accountRepository.findAll()).willReturn(Arrays.asList(account));
            final ResultActions result = mockMvc.perform(get(BASE_PATH));
            result.andExpect(status().isOk());
            result
                    .andExpect(jsonPath("links[0].rel", is("self")))
                    .andExpect(jsonPath("links[0].href", is(BASE_PATH)))
                    .andExpect(jsonPath("content[0].account.id", is(account.getId().intValue())))
                    .andExpect(jsonPath("content[0].account.accountNumber", is(account.getAccountNumber())))
                    .andExpect(jsonPath("content[0].links[0].rel", is("account")))
                    .andExpect(jsonPath("content[0].links[0].href", is(BASE_PATH)))
                    .andExpect(jsonPath("content[0].links[1].rel", is("transactions")))
                    .andExpect(
                            jsonPath("content[0].links[1].href", is(BASE_PATH + "/" + ID + TRANSACTIONS_PATH)))
                    .andExpect(jsonPath("content[0].links[2].rel", is("self")))
                    .andExpect(jsonPath("content[0].links[2].href", is(BASE_PATH + "/" + ID)));
        }

    }

