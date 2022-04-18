package org.accountmanagement.service;

import org.accountmanagement.entity.Account;
import org.accountmanagement.entity.Customer;
import org.accountmanagement.exception.AccountException;
import org.accountmanagement.exception.CustomerException;
import org.accountmanagement.model.ApiResponse;
import org.accountmanagement.repo.AccountRepo;
import org.accountmanagement.repo.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AccountService {

    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private CustomerRepo customerRepo;

    public Customer createAccount(Account account, Integer customerId) {

    Customer customer=customerRepo.findById(customerId)
                .orElseThrow(()->new AccountException("Customer not found"));
     Set<Account> accounts=   customer.getAccounts();
     if(accounts.size()==0){
         accounts=new HashSet<>();
     }
        account.setIsActive(true);
        accounts.add(account);
        customer.setAccounts(accounts);
       return  customerRepo.save(customer);

    }

    public ApiResponse deactivateAccount(Integer accountNumber) {
        Account account= accountRepo
                .findByAccountNumber(accountNumber)
                .orElseThrow(
                        () -> new CustomerException("Account not found with Account number: " + accountNumber));
        account.setIsActive(false);
        accountRepo.save(account);
        return ApiResponse.builder()
                .message("Account deactivated successfully..")
                .build();
    }


    //Activate  customer present in system based on customerId
    public ApiResponse activateAccount(Integer accountNumber) {
        Account account= accountRepo
                .findByAccountNumber(accountNumber)
                .orElseThrow(
                        () -> new CustomerException("Account not found with Account number: " + accountNumber));
        account.setIsActive(true);
        accountRepo.save(account);
        return ApiResponse.builder()
                .message("Account activated successfully..")
                .build();
    }

    public ApiResponse updateAccount(Integer accountNumber, Account account) {

        Account accountFound= accountRepo
                .findByAccountNumber(accountNumber)
                .orElseThrow(
                        () -> new CustomerException("Account not found with Account number: " + accountNumber));
        accountFound.setTotalAmount(account.getTotalAmount());
        accountFound.setBankAddress(account.getBankAddress());
        accountRepo.save(accountFound);
        return ApiResponse.builder()
                .message("Account updated successfully..")
                .build();
    }
}
