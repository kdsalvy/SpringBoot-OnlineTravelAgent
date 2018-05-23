package com.putnam.online.travel.agent.application.booking.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.putnam.online.travel.agent.application.booking.model.entity.Customer;
import com.putnam.online.travel.agent.application.booking.model.repository.CustomerRepository;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Long addCustomer(Customer customer) {
	return customerRepository.save(customer).getId();
    }

    public List<Customer> fetchAllCustomers() {
	List<Customer> customerList = new ArrayList<>();
	customerRepository.findAll().forEach(customerList::add);
	return customerList;
    }

    public Optional<Customer> fetchCustomerById(Long id) {
	return customerRepository.findById(id);
    }

}
