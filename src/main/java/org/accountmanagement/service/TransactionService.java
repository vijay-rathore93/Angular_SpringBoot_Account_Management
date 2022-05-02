package org.accountmanagement.service;


import org.accountmanagement.entity.Account;
import org.accountmanagement.entity.Customer;
import org.accountmanagement.entity.Transaction;
import org.accountmanagement.exception.AccountException;
import org.accountmanagement.exception.CustomerException;
import org.accountmanagement.model.ApiResponse;
import org.accountmanagement.model.TransactionMO;
import org.accountmanagement.repo.AccountRepo;
import org.accountmanagement.repo.TransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepo transactionRepo;

    @Autowired
    private AccountRepo accountRepo;

    //Activate  customer present in system based on customerId
    public ApiResponse voidTransaction(Integer txId) {
        Transaction transaction = transactionRepo
                .findById(txId)
                .orElseThrow(
                        () -> new CustomerException("Transaction not found with Id: " + txId));
        transaction.setIsActive(false);
        transactionRepo.save(transaction);
        return ApiResponse.builder()
                .message("Transaction cancelled successfully..")
                .build();
    }

    public ApiResponse depositAmount(TransactionMO transactionMO) {
        Account accountTo = accountRepo.findByAccountNumber(transactionMO.getToAccount()).get();
        accountTo.setTotalAmount(accountTo.getTotalAmount() + transactionMO.getAmountTobeDeposit());
        accountRepo.save(accountTo);
        return ApiResponse.builder()
                .message("Amount deposited successfully..")
                .build();
    }

    public ApiResponse transferAmount(TransactionMO transactionMO) {
        Account accountFrom = accountRepo.findByAccountNumber(transactionMO.getFromAccount()).get();
        if (accountFrom.getTotalAmount() < transactionMO.getAmountTobeDeposit()) {
            throw new AccountException("Insufficient amount...");
        }
        accountFrom.setTotalAmount(accountFrom.getTotalAmount() - transactionMO.getAmountTobeDeposit());
        accountRepo.save(accountFrom);
        Account accountTo = accountRepo.findByAccountNumber(transactionMO.getToAccount()).get();
        accountTo.setTotalAmount(accountFrom.getTotalAmount() + transactionMO.getAmountTobeDeposit());
        accountRepo.save(accountTo);
        return ApiResponse.builder()
                .message("Amount Withdraw successfully..")
                .build();
    }
}
