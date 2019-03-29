package com.ddkolesnik.bitrixflowsintegration.repository;

import com.ddkolesnik.bitrixflowsintegration.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Alexandr Stegnin
 */
@Repository
public interface ContactRepository extends JpaRepository<Contact, String> {
}
