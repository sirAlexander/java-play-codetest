package com.qudini.webcodetest.controller;

import java.util.Comparator;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.qudini.webcodetest.model.Customer;
import com.qudini.webcodetest.repository.CustomerRepositoryInMemoryImpl;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
public class CustomerController {
	
	@Autowired
	private CustomerRepositoryInMemoryImpl customerRepository;

	
	
	@GetMapping("/customers")
	public Flux<Customer> getAllCustomers(){
		return customerRepository.getAllCustomers();
	}
	
	
	@PostMapping("/bulk/customers")
	public Mono<List<Customer>> loadCustomers(@Valid @RequestBody List<Customer> customers) {
		return customerRepository.saveAllCustomers(customers)
						.sort(Comparator.comparing(Customer::getDuetime)).collectList();	
	}

}
