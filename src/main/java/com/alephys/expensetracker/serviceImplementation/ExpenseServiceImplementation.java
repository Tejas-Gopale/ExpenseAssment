package com.alephys.expensetracker.serviceImplementation;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.alephys.expensetracker.entity.Transaction;
import com.alephys.expensetracker.entity.User;
import com.alephys.expensetracker.enums.TransactionType;
import com.alephys.expensetracker.repository.TransactionRepository;
import com.alephys.expensetracker.repository.UsersRepository;
import com.alephys.expensetracker.service.ExpenseService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Service
@RequiredArgsConstructor
@Slf4j
public class ExpenseServiceImplementation implements ExpenseService {

	private final UsersRepository userRepo;
    private final TransactionRepository txnRepo;
	
    private User getAuthenticatedUser() {
    	        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	        return userRepo.findByEmail(authentication.getName())
    	                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    	
    	    }
    
	@Override
	public void addTransaction(TransactionType type, String category, BigDecimal amount,
			LocalDate parse) {
		User user = getAuthenticatedUser();
		
		 
        Transaction txn =Transaction.builder()
        		.user(user)
        		.type(type)
        		.category(category)
        		.amount(amount)
        		.date(parse).build();
        txnRepo.save(txn);
	}

	@Override
	public Map<String, BigDecimal> getMonthlySummary(int year, int month) {
		User user = getAuthenticatedUser();
		        LocalDate start = LocalDate.of(year, month, 1);
		        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());
		        List<Transaction> txns = txnRepo.findByUserAndDateBetween(user, start, end);
		        Map<String, BigDecimal> summary = new HashMap<>();
		        for (Transaction txn : txns) {
		            String key = txn.getType() + ":" + txn.getCategory();
		            summary.put(key, summary.getOrDefault(key, BigDecimal.ZERO).add(txn.getAmount()));
		        }
		        return summary;
	}

	@Override
	 public void loadFromExcelFile(File file) throws IOException {
User user = getAuthenticatedUser();
        
        try (FileInputStream fis = new FileInputStream(file);
             XSSFWorkbook workbook = new XSSFWorkbook(fis)) {
            XSSFSheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {

            	if(row.getRowNum() == 0) continue;
            	//String userEmail = row.getCell(0).getStringCellValue();
            		///	log.info(":"+row.getCell(0).toString());
            	            String type = row.getCell(0).getStringCellValue();
            	            String category = row.getCell(1).getStringCellValue();
            	            BigDecimal amount = new BigDecimal(row.getCell(2).getNumericCellValue());
            	           LocalDate date = row.getCell(3).getLocalDateTimeCellValue().toLocalDate();
                
            	           addTransaction(TransactionType.valueOf(type.toUpperCase()), category, amount, date);
            }
        }
	}	

	
}
