package com.alephys.expensetracker.controller;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alephys.expensetracker.enums.TransactionType;
import com.alephys.expensetracker.service.ExpenseService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/expenses")
@RequiredArgsConstructor
public class ExpenseController {

	@Autowired
	private  ExpenseService service;
	
	@PostMapping("/add")
    public ResponseEntity<String> add(@RequestParam TransactionType type,
                                      @RequestParam String category,
                                      @RequestParam BigDecimal amount,
                                      @RequestParam String date) {
        service.addTransaction(type, category, amount, LocalDate.parse(date));
        return ResponseEntity.ok("Transaction added.");
    }

    @GetMapping("/summary")
    public ResponseEntity<Map<String, BigDecimal>> summary( @RequestParam int year,
                                                            @RequestParam int month) {
        return ResponseEntity.ok(service.getMonthlySummary( year, month));
    }

    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) throws IOException {
        File temp = File.createTempFile("upload", ".xlsx");
        file.transferTo(temp);
        service.loadFromExcelFile(temp);
        return ResponseEntity.ok("Excel file processed successfully.");
    }
}
