package com.qudini.webcodetest.repository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.qudini.webcodetest.model.Customer;

import reactor.core.publisher.Flux;

@Component
public class CustomerRepositoryInMemoryImpl implements CustomerRepository<Customer, Long> {
	
	private final Map<Long, Customer> customerMap = new ConcurrentHashMap<>();

	@Override
	public Flux<Customer> getAllCustomers() {		
		return Flux.fromIterable(this.customerMap.values());
	}

	@Override
	public Flux<Customer> saveAllCustomers(List<Customer> customers) {	
		customers.forEach(customer -> customerMap.put(customer.getId(), customer));
		return Flux.fromIterable(customers);
	}


}
