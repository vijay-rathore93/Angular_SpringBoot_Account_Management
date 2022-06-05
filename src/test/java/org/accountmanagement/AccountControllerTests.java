package org.accountmanagement;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.accountmanagement.controller.AccountController;
import org.accountmanagement.entity.Account;
import org.accountmanagement.entity.Customer;
import org.accountmanagement.model.ApiResponse;
import org.accountmanagement.service.AccountService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(AccountController.class)
public class AccountControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    AccountService accountService;

    @Test
    public void createCustomerAPI() throws Exception {
        Account account=  getAccount();
        account.setCustomerId(1);
        Mockito.when(accountService.createAccount(getAccount(),1)).thenReturn(getCustomer());
        mvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/account/")
                        .content(asJsonString(getAccount()))
                        .header("customerId",1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.customerId").exists());
        Mockito.verify(accountService,Mockito.times(1)).createAccount(getAccount(),1);
    }

    @Test
    public void getAllCustomerAPI() throws Exception {
        Account account=  getAccount();
        account.setCustomerId(1);
        Mockito.when(accountService.getAccounts()).thenReturn(Arrays.asList(account));
        MvcResult mvcResult= mvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/account/all")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();
        String response= mvcResult.getResponse().toString();
        Mockito.verify(accountService,Mockito.times(1)).getAccounts();
    }

    @Test
    public void getAccountByIdAPI() throws Exception {
        Account account=  getAccount();
        account.setCustomerId(1);
        Mockito.when(accountService.getAccount(1)).thenReturn(account);
        mvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/account/")
                        .header("accountId",1234567890)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
               ;
        Mockito.verify(accountService,Mockito.times(1)).getAccount(1234567890);
    }

    @Test
    public void getAccountDeActivateAPI() throws Exception {
        Account account=  getAccount();
        account.setCustomerId(1);
        Mockito.when(accountService.deactivateAccount(1234567890)).thenReturn( ApiResponse.builder().build());
        mvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/account/deactivate")
                        .header("accountNumber",1234567890)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isAccepted());
        Mockito.verify(accountService,Mockito.times(1)).deactivateAccount(1234567890);
    }

    @Test
    public void getAccountActivateAPI() throws Exception {
        Account account=  getAccount();
        account.setCustomerId(1);
        Mockito.when(accountService.activateAccount(1234567890)).thenReturn( ApiResponse.builder().build());
        mvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/account/activate")
                        .header("accountNumber",1234567890)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isAccepted());
        Mockito.verify(accountService,Mockito.times(1)).activateAccount(1234567890);
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Account getAccount() {
        return Account.builder()
                .accountNumber(123455)
                .bankName("HDFC")
                .branchCode("HDFC1234")
                .bankAddress("testAddress")
                .build();
    }

    private static Customer getCustomer() {
        return Customer.builder()
                .customerId(1)
                .firstName("Saira")
                .lastName("Bano")
                .city("Karnataka")
                .ssn("testSSN")
                .state("KA")
                .country("India")
                .build();
    }
}
