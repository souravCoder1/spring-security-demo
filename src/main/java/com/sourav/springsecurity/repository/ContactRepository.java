package com.sourav.springsecurity.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.eazybytes.model.Contact;

@Repository
public interface ContactRepository extends CrudRepository<Contact, Long> {
	
	
}
