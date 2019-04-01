package com.ddkolesnik.bitrixflowsintegration.service;

import com.ddkolesnik.bitrixflowsintegration.model.BitrixResult;
import com.ddkolesnik.bitrixflowsintegration.model.Contact;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Alexandr Stegnin
 * <p>
 * Сервис, который получет информацию из API Bitrix и сохраняет её в базу данных ДДК
 */

@Service
public class ScheduledService {

    private final ContactService contactService;

    private static final Logger LOG = LoggerFactory.getLogger(ContactServiceImpl.class);

    @Autowired
    public ScheduledService(ContactService contactService) {
        this.contactService = contactService;
    }

    /**
     * Обновление информации по контактам из Bitrix
     * запускается каждый день в 01:00
     */
    @Scheduled(cron = "${spring.config.cron.expression}")
    public void scheduledGetContactsFromBitrix() {
        BitrixResult bitrixResult = contactService.getBitrixResult(0);
        List<Contact> contacts = contactService.getContactsList(bitrixResult);
        contacts = contactService.saveContacts(contacts);
        LOG.info("Saved contacts list size: " + contacts.size());
    }

}
