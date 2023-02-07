package com.sourav.springsecurity.controller;

import java.util.List;

import com.sourav.springsecurity.model.Customer;
import com.sourav.springsecurity.model.Loans;
import com.sourav.springsecurity.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoansController {
	
	@Autowired
	private LoanRepository loanRepository;
	
	@PostMapping("/myLoans")
	public List<Loans> getLoanDetails(@RequestBody Customer customer) {
		List<Loans> loans = loanRepository.findByEmailOrderByStartDtDesc(customer.getEmail());
		if (loans != null ) {
			return loans;
		}else {
			return null;
		}
	}

}
