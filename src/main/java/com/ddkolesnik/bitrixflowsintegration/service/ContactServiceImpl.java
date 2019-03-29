package com.ddkolesnik.bitrixflowsintegration.service;

import com.ddkolesnik.bitrixflowsintegration.model.BitrixResult;
import com.ddkolesnik.bitrixflowsintegration.model.Contact;
import com.ddkolesnik.bitrixflowsintegration.model.ContactFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexandr Stegnin
 */

@Service
public class ContactServiceImpl implements ContactService {

    private final RestTemplate restTemplate;

    private static final String MIME_TYPE = "application/json";
    private static final String USER_AGENT = "Spring 5 WebClient";
    private static final Logger logger = LoggerFactory.getLogger(ContactServiceImpl.class);

    private static String BITRIX_API_BASE_URL;
    private static String BITRIX_ACCESS_KEY;
    private static String BITRIX_CRM_CONTACT_LIST;

    @Autowired
    public ContactServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
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
        ContactFilter filter = new ContactFilter();
        HttpEntity<ContactFilter> requestEntity = new HttpEntity<>(filter);
        ResponseEntity<BitrixResult> resultResponseEntity = restTemplate.exchange(
                BITRIX_API_BASE_URL + BITRIX_ACCESS_KEY + BITRIX_CRM_CONTACT_LIST,
                HttpMethod.POST, requestEntity, BitrixResult.class);
        BitrixResult result = resultResponseEntity.getBody();
        List<Contact> contacts = new ArrayList<>();
        if (result != null) {
            contacts = result.getResult();
        }
        return contacts;
    }
}
