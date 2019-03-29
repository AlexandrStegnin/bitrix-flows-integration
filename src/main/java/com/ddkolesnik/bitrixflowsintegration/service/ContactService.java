package com.ddkolesnik.bitrixflowsintegration.service;

import com.ddkolesnik.bitrixflowsintegration.model.Contact;

import java.util.List;

/**
 * @author Alexandr Stegnin
 */

public interface ContactService {

    List<Contact> getContactsList();

    List<Contact> saveContacts(List<Contact> contacts);

}
