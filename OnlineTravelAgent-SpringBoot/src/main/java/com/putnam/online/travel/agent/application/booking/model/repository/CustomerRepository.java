package com.putnam.online.travel.agent.application.booking.model.repository;

import org.springframework.data.repository.CrudRepository;

import com.putnam.online.travel.agent.application.booking.model.entity.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long>{

}
