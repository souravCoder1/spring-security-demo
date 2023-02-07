package com.sourav.springsecurity.controller;

import java.util.List;

import com.sourav.springsecurity.model.Cards;
import com.sourav.springsecurity.model.Customer;
import com.sourav.springsecurity.repository.CardsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CardsController {
	
	@Autowired
	private CardsRepository cardsRepository;
	
	@PostMapping("/myCards")
	public List<Cards> getCardDetails(@RequestBody Customer customer) {
		List<Cards> cards = cardsRepository.findByEmail(customer.getEmail());
		if (cards != null ) {
			return cards;
		}else {
			return null;
		}
	}

}
