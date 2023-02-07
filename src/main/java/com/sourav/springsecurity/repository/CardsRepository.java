package com.sourav.springsecurity.repository;

import java.util.List;

import com.sourav.springsecurity.model.Cards;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CardsRepository extends CrudRepository<Cards, Long> {
	
	List<Cards> findByEmail(String email);

}
