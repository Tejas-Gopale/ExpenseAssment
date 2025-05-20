package com.alephys.expensetracker.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alephys.expensetracker.entity.Transaction;
import com.alephys.expensetracker.entity.User;

public interface TransactionRepository extends JpaRepository<Transaction, Long>{
	List<Transaction> findByUserAndDateBetween(User user, LocalDate start, LocalDate end);
}
