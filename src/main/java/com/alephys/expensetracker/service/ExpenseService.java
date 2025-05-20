package com.alephys.expensetracker.service;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

import com.alephys.expensetracker.enums.TransactionType;

public interface ExpenseService {

	void addTransaction(String email, TransactionType type, String category, BigDecimal amount, LocalDate parse);

	public Map<String, BigDecimal> getMonthlySummary(String email, int year, int month);

	//void loadFromFile(File temp) throws IOException;

	void loadFromExcelFile(File temp) throws IOException;

//	void registerUser(String email, String password);;
}
