package com.ddkolesnik.bitrixflowsintegration.service;

import com.ddkolesnik.bitrixflowsintegration.model.BitrixResult;
import com.ddkolesnik.bitrixflowsintegration.model.Contact;
import com.ddkolesnik.bitrixflowsintegration.model.ContactFilter;
import com.ddkolesnik.bitrixflowsintegration.repository.ContactRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author Alexandr Stegnin
 */

@Service
public class ContactServiceImpl implements ContactService {

    private final RestTemplate restTemplate;
    private final ContactFilter filter;
    private final ContactRepository contactRepository;

    private static final String USER_AGENT = "Spring 5 WebClient";
    private static final Logger logger = LoggerFactory.getLogger(ContactServiceImpl.class);

    private static String BITRIX_API_BASE_URL;
    private static String BITRIX_ACCESS_KEY;
    private static String BITRIX_CRM_CONTACT_LIST;

    @Autowired
    public ContactServiceImpl(RestTemplate restTemplate, ContactFilter filter,
                              ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
        this.restTemplate = restTemplate;
        this.filter = filter;
    }

    @Value("${bitrix.default.url}")
    public void setBitrixApiBaseUrl(String value) {
        BITRIX_API_BASE_URL = value;
    }

    @Value("${bitrix.access.key}")
    public void setBitrixAccessKey(String value) {
        BITRIX_ACCESS_KEY = value;
    }

    @Value("${bitrix.crm.contact.list}")
    public void setBitrixCrmContactList(String value) {
        BITRIX_CRM_CONTACT_LIST = value;
    }

    @Override
    public List<Contact> getContactsList() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(new ArrayList<>(Collections.singletonList(MediaType.APPLICATION_JSON)));
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        headers.add(HttpHeaders.USER_AGENT, USER_AGENT);
        HttpEntity<ContactFilter> requestEntity = new HttpEntity<>(filter, headers);
        ResponseEntity<BitrixResult> resultResponseEntity = restTemplate.exchange(
                BITRIX_API_BASE_URL + BITRIX_ACCESS_KEY + BITRIX_CRM_CONTACT_LIST,
                HttpMethod.POST, requestEntity, BitrixResult.class);
        BitrixResult result = resultResponseEntity.getBody();
        List<Contact> contacts = new ArrayList<>();
        if (result != null) {
            contacts = result.getResult();
        } else {
            logger.error("Error getting contacts info, body is empty");
        }
        contacts.forEach(contact -> {
            if (!Objects.equals(null, contact.getEmail())) {
                contact.getEmail().forEach(email -> {
                    email.setContact(contact);
                });
            }
        });
        return contacts;
    }

    @Override
    public List<Contact> saveContacts(List<Contact> contacts) {
        return contactRepository.saveAll(contacts);
    }
}
