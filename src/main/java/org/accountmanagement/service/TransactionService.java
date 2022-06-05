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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

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
        transaction.setType("VOID");
        transaction.setStatus("Cancelled");
        transactionRepo.save(transaction);
        return ApiResponse.builder()
                .message("Transaction cancelled successfully..")
                .build();
    }

    public ApiResponse depositAmount(TransactionMO transactionMO) {
        Transaction transaction = Transaction.builder()
                .fromAccount(transactionMO.getFromAccount())
                //.toAccount(transactionMO.getToAccount())
                .transactionDate(LocalDate.now())
                .amountTobeTransferred(transactionMO.getAmountTobeDeposit())
                .type("DEPOSIT")
                .status("In-Progress")
                .build();

        transactionRepo.save(transaction);
        return ApiResponse.builder()
                .message("Amount Deposit is in Progress, It takes 2-3 minutes ..")
                .build();
    }

    public ApiResponse withdrawAmount(TransactionMO transactionMO) {

        Account accountFrom = accountRepo.findByAccountNumber(transactionMO.getFromAccount()).get();
        if (accountFrom.getTotalAmount() < transactionMO.getAmountToBeWithDraw()) {
            throw new AccountException("Insufficient amount...");
        }
        Transaction transaction = Transaction.builder()
                .fromAccount(transactionMO.getFromAccount())
                //.toAccount(transactionMO.getToAccount())
                .transactionDate(LocalDate.now())
                .amountTobeTransferred(transactionMO.getAmountToBeWithDraw())
                .type("WITHDRAW")
                .status("In-Progress")
                .build();

        transactionRepo.save(transaction);
        return ApiResponse.builder()
                .message("Amount Deposit is in Progress, It takes 2-3 minutes ..")
                .build();
    }

    public ApiResponse transferAmount(TransactionMO transactionMO) {
        Account accountFrom = accountRepo.findByAccountNumber(transactionMO.getFromAccount()).get();
        if (accountFrom.getTotalAmount() < transactionMO.getAmountTobeDeposit()) {
            throw new AccountException("Insufficient amount...");
        }
        Transaction transaction = Transaction.builder()
                .fromAccount(transactionMO.getFromAccount())
                .toAccount(transactionMO.getToAccount())
                .transactionDate(LocalDate.now())
                .amountTobeTransferred(transactionMO.getAmountToBeWithDraw())
                .status("In-Progress")
                .build();

        transactionRepo.save(transaction);
        return ApiResponse.builder()
                .message("Amount Withdraw is in Progress, It takes 2-3 minutes ..")
                .build();
    }

    public List<Transaction> getAllTransaction() {
        return transactionRepo.findAll();
    }

    @Scheduled(initialDelay = 10000, fixedRate = 20000)
    public void process() {
        Boolean accountFlag = false;
        Boolean transactionFlag = false;
        List<Transaction> transactions = transactionRepo.findAll();
        for (Transaction obj : transactions) {
            Account account = accountRepo.findByAccountNumber(obj.getFromAccount()).get();
            if (obj.getStatus().equalsIgnoreCase("In-Progress")
                    && obj.getType().equalsIgnoreCase("DEPOSIT")) {
                account.setTotalAmount(account.getTotalAmount() + obj.getAmountTobeTransferred());
                obj.setStatus("COMPLETED");
                accountFlag = true;
                transactionFlag = true;
            } else if (obj.getStatus().equalsIgnoreCase("In-Progress")
                    && obj.getType().equalsIgnoreCase("WITHDRAW")) {
                if (account.getTotalAmount() > obj.getAmountTobeTransferred()) {
                    account.setTotalAmount(account.getTotalAmount() - obj.getAmountTobeTransferred());
                    obj.setStatus("COMPLETED");
                } else {
                    obj.setStatus("DECLINED");
                }
                accountFlag = true;
                transactionFlag = true;
            }

            if (transactionFlag) {
                transactionRepo.saveAll(transactions);
            }

            if (accountFlag) {
                accountRepo.save(account);
            }
        }


    }
}
