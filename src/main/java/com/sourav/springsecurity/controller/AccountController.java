package com.sourav.springsecurity.controller;

import com.sourav.springsecurity.model.Accounts;
import com.sourav.springsecurity.model.Customer;
import com.sourav.springsecurity.repository.AccountsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {
	
	@Autowired
	AccountsRepository accountsRepository;
	
	@PostMapping("/myAccount")
	public Accounts getAccountDetails(@RequestBody Customer customer) {
		Accounts accounts = accountsRepository.findByEmail(customer.getEmail());
		if (accounts != null ) {
			return accounts;
		}else {
			return null;
		}
	}

}
