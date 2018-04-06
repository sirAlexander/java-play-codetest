package com.qudini.webcodetest.controller;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.Part;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
	public Mono<List<Customer>> bulkLoadCustomers(@Valid @RequestBody List<Customer> customers) {
		return customerRepository.saveAllCustomers(customers)
						.sort(Comparator.comparing(Customer::getDuetime)).collectList();	
	}
	
	@PostMapping("/bulk/customers/upload")
	public Mono<String> uploadCustomersInBulk(@RequestParam("file") MultipartFile file) {
		System.out.println(file.toString());
		/**return customerRepository.saveAllCustomers(customers)
						.sort(Comparator.comparing(Customer::getDuetime)).collectList();**/
		return Mono.empty();	
	}
	
	
	
	// Customers are sent to the client as Server Sent Events
	@GetMapping(value = "/stream/customers", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<Customer> streamAllCustomers(){
		return customerRepository.getAllCustomers();
	}

}
