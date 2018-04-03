package com.qudini.webcodetest.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.joda.time.DateTime;


public class Customer {

	@NotNull
	private Long id;
	@NotBlank
	private String name;
	@NotNull
	private DateTime duetime;
	@NotNull
	private DateTime jointime;

	public Customer () {
		
	}
	
	public Customer(Long id, String name, DateTime duetime, DateTime jointime) {
		this.id = id;
		this.name = name;
		this.duetime = duetime;
		this.jointime = jointime;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public DateTime getDuetime() {
		return duetime;
	}

	public DateTime getJointime() {
		return jointime;
	}	
	

}
