package com.qudini.webcodetest.repository;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.qudini.webcodetest.model.Customer;

import reactor.core.publisher.Flux;


@Repository
public interface CustomerRepository {

	Flux<Customer> getAllCustomers();

	Flux<Customer> saveAllCustomers(List<Customer> customers);


}
