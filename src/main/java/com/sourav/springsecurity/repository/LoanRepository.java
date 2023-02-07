package com.sourav.springsecurity.repository;

import java.util.List;

import com.sourav.springsecurity.model.Loans;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRepository extends CrudRepository<Loans, Long> {
	
	List<Loans> findByEmailOrderByStartDtDesc(String email);

}
