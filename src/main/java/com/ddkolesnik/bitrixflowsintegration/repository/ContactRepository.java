package com.ddkolesnik.bitrixflowsintegration.repository;

import com.ddkolesnik.bitrixflowsintegration.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Alexandr Stegnin
 * <p>
 * Репозиторий для сохранения списка контактов в базе данных ДДК
 */
@Repository
public interface ContactRepository extends JpaRepository<Contact, String> {
}
