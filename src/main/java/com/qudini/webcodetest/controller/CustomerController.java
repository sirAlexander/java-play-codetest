package com.qudini.webcodetest.controller;

import java.util.Comparator;
import java.util.List;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import com.qudini.webcodetest.model.Customer;
import com.qudini.webcodetest.repository.CustomerRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
public class CustomerController {
	
	@Autowired
	private CustomerRepository customerRepository;

	
	
	@GetMapping("/customers")
	public Flux<Customer> getAllCustomers(){
		return customerRepository.getAllCustomers();
	}
	
	
	@PostMapping("/bulk/customers")
	public Mono<List<Customer>> bulkLoadCustomers(@Valid @RequestBody List<Customer> customers) {
		return customerRepository.saveAllCustomers(customers)
						.sort(Comparator.comparing(Customer::getDuetime)).collectList();	
	}
	
	@PostMapping(value = "/bulk/customers/upload", 
			consumes = MediaType.MULTIPART_FORM_DATA_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Mono<String> uploadCustomersInBulk(@RequestPart("file") Mono<FilePart> file) {
		file.map(CustomerController::partAsString);
		/**return customerRepository.saveAllCustomers(customers)
						.sort(Comparator.comparing(Customer::getDuetime)).collectList();**/
		return Mono.empty();	
	}
	
	private static String partAsString(FilePart part) {
		return part.toString();
	}
	
	
	
	// Customers are sent to the client as Server Sent Events
	@PostMapping(value = "/stream/customers", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<Customer> streamAllCustomers(){
		return customerRepository.getAllCustomers();
	}

}
