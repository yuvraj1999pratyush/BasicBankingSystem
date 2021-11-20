package com.mkg;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {
	
	Customer findById(long id);
    List<Customer> findAll();
    void deleteById(int id);
    
    
    
 
}