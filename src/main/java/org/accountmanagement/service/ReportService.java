package org.accountmanagement.service;

import org.accountmanagement.util.PDFGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ReportService {

    @Autowired
    private PDFGenerator pdfGenerator;


    public void generateReport(LocalDate date) {
        pdfGenerator.generatePdfReport();
    }
}
