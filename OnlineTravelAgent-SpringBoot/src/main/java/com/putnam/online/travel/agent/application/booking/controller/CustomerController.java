package com.putnam.online.travel.agent.application.booking.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.putnam.online.travel.agent.application.booking.model.entity.Customer;
import com.putnam.online.travel.agent.application.booking.service.CustomerService;

@RestController
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping(path = "customers", consumes = "application/json", produces = "application/json")
    public Long addCustomer(@RequestBody Customer customer) {
	return customerService.addCustomer(customer);
    }

    @GetMapping(path = "customers", produces = "application/json")
    public List<Customer> fetchAllCustomers() {
	return customerService.fetchAllCustomers();
    }

    @GetMapping(path = "customers/{id}", produces = "application/json")
    public Optional<Customer> fetchCustomerById(@PathVariable("id") Long id) {
	return customerService.fetchCustomerById(id);
    }
}
