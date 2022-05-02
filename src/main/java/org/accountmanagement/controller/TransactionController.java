package org.accountmanagement.controller;

import org.accountmanagement.model.ApiResponse;
import org.accountmanagement.model.TransactionMO;
import org.accountmanagement.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/v1/transaction/")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/void")
    public ResponseEntity<ApiResponse> voidTransaction(@RequestHeader("txId") Integer txId){
        return new ResponseEntity<>(transactionService.voidTransaction(txId), HttpStatus.ACCEPTED);
    }

    @PostMapping("/deposit")
    public ResponseEntity<ApiResponse> depositAmount(@RequestBody TransactionMO transactionMO){
        return new ResponseEntity<>(transactionService.depositAmount(transactionMO), HttpStatus.ACCEPTED);
    }

    @PostMapping("/transfer")
    public ResponseEntity<ApiResponse> transfer(@RequestBody TransactionMO transactionMO){
        return new ResponseEntity<>(transactionService.depositAmount(transactionMO), HttpStatus.ACCEPTED);
    }


}
