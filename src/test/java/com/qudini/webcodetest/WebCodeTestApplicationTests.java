package com.qudini.webcodetest;



import java.util.Arrays;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.joda.time.DateTime;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.qudini.webcodetest.model.Customer;


import reactor.core.publisher.Flux;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebCodeTestApplicationTests {
	
	@Autowired
	private WebTestClient webTestClient;
	
	static List<Customer> customers;
	
	@BeforeClass
	public static void setUps() {		
		customers = Arrays.asList(
				new Customer(Long.valueOf(11110001), "Grey Joy", new DateTime(2012, 4, 25, 7, 0, 0, 0),
						new DateTime(2012, 6, 28, 11, 0, 0, 0)),
				new Customer(Long.valueOf(11110002), "Iron Born", new DateTime(2013, 2, 16, 12, 0, 0, 0),
						new DateTime(2014, 5, 19, 6, 0, 0, 0)),
				new Customer(Long.valueOf(11110002), "shaka zulu", new DateTime(2011, 5, 11, 6, 0, 0, 0),
						new DateTime(2015, 3, 15, 1, 0, 0, 0)));		
	}

	@Test
	public void testBulkLoad() {
		
		webTestClient.post().uri("/bulk/customers")
				.contentType(MediaType.APPLICATION_JSON_UTF8)							
				.body(Flux.fromIterable(customers), Customer.class)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
				.expectBody()
				.jsonPath("$[0]['id']").isNotEmpty()
				.jsonPath("$[0]['name']").isEqualTo("shaka zulu")
				.consumeWith(response -> 
						Assertions.assertThat(response.getResponseBody()).isNotNull());		
	}
	
	
	@Test
	public void testGetAllCustomers() {
		webTestClient.get().uri("/customers")
			.accept(MediaType.APPLICATION_JSON_UTF8)
			.exchange()
			.expectStatus().isOk()
			.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
			.expectBodyList(Customer.class);
	}



}
