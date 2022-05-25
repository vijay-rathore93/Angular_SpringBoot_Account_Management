package org.accountmanagement.controller;

import java.util.List;

import org.accountmanagement.entity.Account;
import org.accountmanagement.entity.Customer;
import org.accountmanagement.model.ApiResponse;
import org.accountmanagement.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/account/")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping
    public ResponseEntity<Customer> createAccount(@RequestBody Account account,
                                                  @RequestHeader("customerId") Integer customerId) {
        return new ResponseEntity<>(accountService.createAccount(account,customerId),
                HttpStatus.CREATED);
    }

    @GetMapping("/deactivate")
    public ResponseEntity<ApiResponse> deactivateAccount(@RequestHeader("accountNumber") Integer accountNumber){
        return new ResponseEntity<>(accountService.deactivateAccount(accountNumber), HttpStatus.ACCEPTED);
    }

    @GetMapping("/activate")
    public ResponseEntity<ApiResponse> activateAccount(@RequestHeader("accountNumber") Integer accountNumber){
        return new ResponseEntity<>(accountService.activateAccount(accountNumber), HttpStatus.ACCEPTED);
    }
    @PostMapping("/update")
    public ResponseEntity<ApiResponse> updateAccount(@RequestHeader("accountNumber") Integer accountNumber,@RequestBody Account account){
        return new ResponseEntity<>(accountService.updateAccount(accountNumber,account), HttpStatus.OK);
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<Account>> getAccounts(){
        return new ResponseEntity<>(accountService.getAccounts(), HttpStatus.OK);
    }
    
    @DeleteMapping
    public ResponseEntity<ApiResponse> deleteAccountInfo(@RequestHeader("accountId") Integer accountId){
    	return new ResponseEntity<>(accountService.deleteAccount(accountId), HttpStatus.OK);
    }
    
    @GetMapping("/")
    public ResponseEntity<Account> getAccount(@RequestHeader("accountId") Integer accountId){
        return new ResponseEntity<>(accountService.getAccount(accountId), HttpStatus.OK);
    }
}
