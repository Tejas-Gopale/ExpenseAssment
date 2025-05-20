package com.alephys.expensetracker.service;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

import com.alephys.expensetracker.enums.TransactionType;

public interface ExpenseService {

	void addTransaction(TransactionType type, String category, BigDecimal amount, LocalDate parse);

	public Map<String, BigDecimal> getMonthlySummary(int year, int month);

	void loadFromExcelFile(File temp) throws IOException;

}
