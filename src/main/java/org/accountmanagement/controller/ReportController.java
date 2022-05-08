package org.accountmanagement.controller;

import org.accountmanagement.model.ApiResponse;
import org.accountmanagement.model.TransactionMO;
import org.accountmanagement.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/report/")
public class ReportController {

    @Autowired
    private ReportService reportService;


    @PostMapping("/report")
    public ResponseEntity<ApiResponse> depositAmount(@RequestParam(required = false) LocalDate date){
        reportService.generateReport(date);
        return new ResponseEntity<>( HttpStatus.ACCEPTED);
    }


}
