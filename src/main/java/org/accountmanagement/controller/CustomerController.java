package org.accountmanagement.controller;


import lombok.RequiredArgsConstructor;
import org.accountmanagement.entity.Customer;
import org.accountmanagement.model.ApiResponse;
import org.accountmanagement.service.CustomerService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customer/")
public class CustomerController {

    private final CustomerService customerService;


    @PostMapping
    public ResponseEntity<Customer> createCustomerInfo(@RequestBody Customer customer){
        return new ResponseEntity<>(customerService.createCustomerInfo(customer), HttpStatus.CREATED);
    }

    @PostMapping("/upload")
    public ResponseEntity<Void> uploadFile(@RequestParam("file") MultipartFile file,@RequestHeader("customerId") Integer customerId) throws Exception {
        customerService.saveImage(file,customerId);
        String  downloadURl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download/"+customerId)
                //.path(attachment.getId())
                .toUriString();
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping("/download/{customerId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Integer customerId)  {
        Customer customer ;
        customer = customerService.getImage(customerId);
        return  ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(customer.getFileContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + customer.getFileName()
                                + "\"")
                .body(new ByteArrayResource(customer.getDocumentToBeVerify()));
    }

    @PostMapping("/deactivate")
    public ResponseEntity<ApiResponse> deactivateCustomer(@RequestHeader("customerId") Integer customerId){
        return new ResponseEntity<>(customerService.deactivateCustomer(customerId), HttpStatus.ACCEPTED);
    }

    @PostMapping("/activate")
    public ResponseEntity<ApiResponse> activateCustomer(@RequestHeader("customerId") Integer customerId){
        return new ResponseEntity<>(customerService.activateCustomer(customerId), HttpStatus.ACCEPTED);
    }

    @GetMapping("/")
    public ResponseEntity<Customer> getCustomer(@RequestHeader("customerId") Integer customerId){
        return new ResponseEntity<>(customerService.getCustomer(customerId), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Customer>> getCustomers(){
        return new ResponseEntity<>(customerService.getCustomers(), HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<Customer> updateCustomerInfo(@RequestBody Customer customer){
        return new ResponseEntity<>(customerService.updateCustomerInfo(customer), HttpStatus.OK);
    }


}
