package org.accountmanagement;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.accountmanagement.controller.CustomerController;
import org.accountmanagement.entity.Customer;
import org.accountmanagement.model.ApiResponse;
import org.accountmanagement.repo.CustomerRepo;
import org.accountmanagement.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
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
@WebMvcTest(CustomerController.class)
class CustomerControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    CustomerService customerService;

    @Test
    public void createCustomerAPI() throws Exception {
        Customer customer=  getCustomer();
        customer.setCustomerId(1);
        Mockito.when(customerService.createCustomerInfo(getCustomer())).thenReturn(customer);
        mvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/customer/")
                        .content(asJsonString(getCustomer()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.customerId").exists());
        Mockito.verify(customerService,Mockito.times(1)).createCustomerInfo(getCustomer());
    }

    @Test
    public void getAllCustomerAPI() throws Exception {
        Customer customer=  getCustomer();
        customer.setCustomerId(1);
        Mockito.when(customerService.getCustomers()).thenReturn(Arrays.asList(customer));
        MvcResult mvcResult= mvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/customer/all")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();
        String response= mvcResult.getResponse().toString();
        Mockito.verify(customerService,Mockito.times(1)).getCustomers();
    }

    @Test
    public void getCustomerByIdAPI() throws Exception {
        Customer customer=  getCustomer();
        customer.setCustomerId(1);
        Mockito.when(customerService.getCustomer(1)).thenReturn(customer);
        mvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/customer/")
                        .header("customerId",1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.customerId").value(1));
        Mockito.verify(customerService,Mockito.times(1)).getCustomer(1);
    }

    @Test
    public void getCustomerDeActivateAPI() throws Exception {
        Customer customer=  getCustomer();
        customer.setCustomerId(1);
        Mockito.when(customerService.deactivateCustomer(1)).thenReturn( ApiResponse.builder().build());
        mvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/customer/deactivate")
                        .header("customerId",1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isAccepted());
        Mockito.verify(customerService,Mockito.times(1)).deactivateCustomer(1);
    }

    @Test
    public void getCustomerActivateAPI() throws Exception {
        Customer customer=  getCustomer();
        customer.setCustomerId(1);
        Mockito.when(customerService.activateCustomer(1)).thenReturn( ApiResponse.builder().build());
        mvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/customer/activate")
                        .header("customerId",1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isAccepted());
        Mockito.verify(customerService,Mockito.times(1)).activateCustomer(1);
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Customer getCustomer() {
        return Customer.builder()
                .firstName("Saira")
                .lastName("Bano")
                .city("Karnataka")
                .ssn("testSSN")
                .state("KA")
                .country("India")
                .build();
    }

}
