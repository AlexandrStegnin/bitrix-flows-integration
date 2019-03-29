package com.ddkolesnik.bitrixflowsintegration.service;

import com.ddkolesnik.bitrixflowsintegration.model.Contact;

import java.util.List;

/**
 * @author Alexandr Stegnin
 */

@FunctionalInterface
public interface ContactService {

    List<Contact> getContactsList();

}
