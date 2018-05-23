package com.putnam.online.travel.agent.application.asset.model.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.putnam.online.travel.agent.application.asset.model.entity.Contact;

public interface ContactRepository extends CrudRepository<Contact, Long> {

    public Optional<Contact> getByNameLike(String expr);
}
