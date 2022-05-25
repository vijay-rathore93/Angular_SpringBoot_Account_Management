package org.accountmanagement.service;

import lombok.RequiredArgsConstructor;
import org.accountmanagement.entity.Customer;
import org.accountmanagement.exception.CustomerException;
import org.accountmanagement.model.ApiResponse;
import org.accountmanagement.repo.CustomerRepo;
import org.accountmanagement.util.Validators;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepo customerRepo;

    // creating record for customer
    public Customer createCustomerInfo(Customer customer) {
        Validators.inputValidation(customer);
        customer.setActive(true);
        return customerRepo.save(customer);
    }


    //uploading  customer  image present in system based on customerId
    public void saveImage(MultipartFile file,Integer customerId) throws Exception {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        Validators.fileValidation(fileName);
        Customer customerFound=  customerRepo.findById(customerId)
                .orElseThrow(()->new CustomerException("Customer not Found!!."));
        customerFound.setFileName(fileName);
        customerFound.setFileContentType(file.getContentType());
        customerFound.setDocumentToBeVerify(file.getBytes());
        customerRepo.save(customerFound);
    }

    //get  customer  image present in system based on customerId
    public Customer getImage(Integer customerId)  {
        return customerRepo
                .findById(customerId)
                .orElseThrow(
                        () -> new CustomerException("File not found with Id: " + customerId));
    }

    //De-Activate  customer present in system based on customerId
    public ApiResponse deactivateCustomer(Integer customerId) {
        Customer customer= customerRepo
                .findById(customerId)
                .orElseThrow(
                        () -> new CustomerException("File not found with Id: " + customerId));
        customer.setActive(false);
        customerRepo.save(customer);
        return ApiResponse.builder()
                .message("Customer deactivated successfully..")
                .build();
    }


    //Activate  customer present in system based on customerId
    public ApiResponse activateCustomer(Integer customerId) {
        Customer customer= customerRepo
                .findById(customerId)
                .orElseThrow(
                        () -> new CustomerException("File not found with Id: " + customerId));
        customer.setActive(true);
        customerRepo.save(customer);
        return ApiResponse.builder()
                .message("Customer activated successfully..")
                .build();
    }


    //get  customer present in system based on customerId
    public Customer getCustomer(Integer customerId) {
        return this.getImage(customerId);
    }


    //get all customer present in system
    public List<Customer> getCustomers() {
        return customerRepo.findAll();
    }

    public Customer updateCustomerInfo(Customer input) {
        Customer  customerFound= this.getImage(input.getCustomerId());
        customerFound.setAddress1(input.getAddress1());
        customerFound.setZipExt(input.getZipExt());
        customerFound.setZipPin(input.getZipPin());
        customerFound.setState(input.getState());
        customerFound.setCountry(input.getCountry());
        Validators.inputValidation(customerFound);
        return customerRepo.save(customerFound);
    }
    
    public ApiResponse deleteCustomer(Integer customerId) {
        customerRepo.deleteById(customerId);
        return ApiResponse.builder()
                .message("Customer deleted successfully..")
                .build();
    }
}
