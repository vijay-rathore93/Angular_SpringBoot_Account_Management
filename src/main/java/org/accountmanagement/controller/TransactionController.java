package org.accountmanagement.controller;

import org.accountmanagement.entity.Transaction;
import org.accountmanagement.model.ApiResponse;
import org.accountmanagement.model.TransactionMO;
import org.accountmanagement.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("api/v1/transaction/")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/void")
    public ResponseEntity<ApiResponse> voidTransaction(@RequestHeader("txId") Integer txId){
        return new ResponseEntity<>(transactionService.voidTransaction(txId), HttpStatus.ACCEPTED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Transaction>> getAllTransaction(){
        return new ResponseEntity<>(transactionService.getAllTransaction(), HttpStatus.OK);
    }

    @PostMapping("/deposit")
    public ResponseEntity<ApiResponse> depositAmount(@RequestBody TransactionMO transactionMO){
        return new ResponseEntity<>(transactionService.depositAmount(transactionMO), HttpStatus.ACCEPTED);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<ApiResponse> withdrawAmount(@RequestBody TransactionMO transactionMO){
        return new ResponseEntity<>(transactionService.withdrawAmount(transactionMO), HttpStatus.ACCEPTED);
    }

    @PostMapping("/transfer")
    public ResponseEntity<ApiResponse> transfer(@RequestBody TransactionMO transactionMO){
        return new ResponseEntity<>(transactionService.depositAmount(transactionMO), HttpStatus.ACCEPTED);
    }


}
