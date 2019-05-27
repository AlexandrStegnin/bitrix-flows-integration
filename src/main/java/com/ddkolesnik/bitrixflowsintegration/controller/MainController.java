package com.ddkolesnik.bitrixflowsintegration.controller;

import com.ddkolesnik.bitrixflowsintegration.model.BitrixResult;
import com.ddkolesnik.bitrixflowsintegration.model.Contact;
import com.ddkolesnik.bitrixflowsintegration.service.ContactService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Alexandr Stegnin
 */

@Slf4j
@RestController
public class MainController {

    private final ContactService contactService;

    @Autowired
    public MainController(ContactService contactService) {
        this.contactService = contactService;
    }


    @GetMapping(path = "/update")
    public String updateContactsInfo() {
        BitrixResult bitrixResult = contactService.getBitrixResult(0);
        List<Contact> contacts = contactService.getContactsList(bitrixResult);
        contacts = contactService.saveContacts(contacts);
        log.info("Saved contacts list size: " + contacts.size());
        return String.format("Saved contacts list size: %d", contacts.size());
    }

}
