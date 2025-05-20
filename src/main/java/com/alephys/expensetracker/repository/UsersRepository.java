package com.alephys.expensetracker.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.alephys.expensetracker.entity.User;


@Repository
public interface UsersRepository extends JpaRepository<User, Long>{

	Optional<User> findByName(String name);

Optional<User> findByEmail(String email);


	
}
