package com.ddkolesnik.bitrixflowsintegration.service;

import com.ddkolesnik.bitrixflowsintegration.model.BitrixResult;
import com.ddkolesnik.bitrixflowsintegration.model.Contact;

import java.util.List;

/**
 * @author Alexandr Stegnin
 * <p>
 * Интерфейс для работы с Bitrix
 */

public interface ContactService {

    List<Contact> getContactsList(BitrixResult bitrixResult);

    List<Contact> saveContacts(List<Contact> contacts);

    BitrixResult getBitrixResult(int start);

}
