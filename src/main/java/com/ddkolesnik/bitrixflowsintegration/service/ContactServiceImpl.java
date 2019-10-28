package com.ddkolesnik.bitrixflowsintegration.service;

import com.ddkolesnik.bitrixflowsintegration.model.BitrixResult;
import com.ddkolesnik.bitrixflowsintegration.model.Contact;
import com.ddkolesnik.bitrixflowsintegration.model.ContactFilter;
import com.ddkolesnik.bitrixflowsintegration.repository.ContactRepository;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Alexandr Stegnin
 * Сервис для получения списка конактов из Bitrix Доходного дома Колесникъ (далее ДДК)
 * и сохранения в базе потоков инвесторов ДДК
 */
@Service
public class ContactServiceImpl implements ContactService {

    private final RestTemplate restTemplate;
    private final ContactRepository contactRepository;
    private HttpEntity<ContactFilter> httpEntity;
    private ContactFilter contactFilter;

    private static final String USER_AGENT = "Spring 5 WebClient";
    private static final Logger logger = LoggerFactory.getLogger(ContactServiceImpl.class);

    private static String BITRIX_API_BASE_URL;
    private static String BITRIX_WEBHOOK_USER_ID;
    private static String BITRIX_ACCESS_KEY;
    private static String BITRIX_CRM_CONTACT_LIST;

    /**
     * Инжектим необходимые бины
     *
     * @param restTemplate      - шаблон для обращения с REST сервисами
     * @param filter            - содержит набор полей выборки, которую мы хотим получить от API Bitrix и параметр start
     * @param contactRepository - репозиторий для сохранения контактов в базе данных
     */
    @Autowired
    public ContactServiceImpl(RestTemplate restTemplate, ContactFilter filter,
                              ContactRepository contactRepository) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        httpHeaders.add(HttpHeaders.USER_AGENT, USER_AGENT);
        this.contactRepository = contactRepository;
        this.restTemplate = restTemplate;
        this.contactFilter = filter;
        this.httpEntity = new HttpEntity<>(contactFilter, httpHeaders);
    }

    /**
     * Устанавливаем значение url API Bitrix из private.properties
     *
     * @param value - значение из файла
     */
    @Value("${bitrix.default.url}")
    public void setBitrixApiBaseUrl(String value) {
        BITRIX_API_BASE_URL = value;
    }

    /**
     * Устанавливаем значение токена для API Bitrix из private.properties
     *
     * @param value - значение из файла
     */
    @Value("${bitrix.access.key}")
    public void setBitrixAccessKey(String value) {
        BITRIX_ACCESS_KEY = value;
    }

    /**
     * Устанавливаем значение точки входа для API Bitrix из private.properties
     *
     * @param value - значение из файла
     */
    @Value("${bitrix.crm.contact.list}")
    public void setBitrixCrmContactList(String value) {
        BITRIX_CRM_CONTACT_LIST = value;
    }

    /**
     * Устанавливаем значение user id из private.properties
     *
     * @param value - значение из файла
     */
    @Value("${bitrix.webhook.user.id}")
    public void setBitrixWebhookUserId(String value) {
        BITRIX_WEBHOOK_USER_ID = value;
    }

    @Override
    public List<Contact> getContactsList(@NotNull BitrixResult bitrixResult) {
        List<Contact> contacts = new ArrayList<>();
        if (!Objects.equals(null, bitrixResult.getError())) {
            logger.error("Error getting contacts info: " + bitrixResult.getError());
        } else {
            contacts = bitrixResult.getResult();
        }
        contacts.forEach(contact -> {
            if (!Objects.equals(null, contact.getEmails())) {
                contact.getEmails().forEach(email -> email.setContact(contact));
            }
        });
        return contacts;
    }

    /**
     * Сохраняем данные контактов, полученные из Bitrix в базу данных ДДК
     *
     * @param contacts - список контактов
     * @return - список контактов, которые сохранили в базу данных
     */
    @Override
    public List<Contact> saveContacts(@NotNull List<Contact> contacts) {
        contactRepository.deleteAll();
        return contactRepository.saveAll(contacts);
    }

    /**
     * Обращаемся к API Bitrix для получении информации по контактам
     *
     * @param start - Bitrix одаёт по 50 записей за раз
     * @return - @see BitrixResult.class
     */
    @Override
    public BitrixResult getBitrixResult(int start) {
        ResponseEntity<BitrixResult> resultResponseEntity = restTemplate.exchange(
                BITRIX_API_BASE_URL + BITRIX_WEBHOOK_USER_ID + BITRIX_ACCESS_KEY + BITRIX_CRM_CONTACT_LIST,
                HttpMethod.POST, httpEntity, BitrixResult.class);
        BitrixResult result = resultResponseEntity.getBody();
        int next;
        BitrixResult totalResult = new BitrixResult();
        // если API вернуло ответ
        if (!Objects.equals(null, result)) {
            // проверяем, может API вернуло ошибку
            if (!Objects.equals(null, result.getError())) {
                // пишем в логи и возвращаем то, что есть на данный момент в результате
                logger.error("Error getting contacts info: " + result.getError());
                return totalResult;
            }
            // если в ответе пришёл параметр next, значит есть ещё записи, которые не вернулись
            if (!Objects.equals(null, result.getNext())) {
                next = Integer.parseInt(result.getNext());
                // устанавливаем значение start в запрос, чтобы следующий ответ API вернул остальные записи (тоже не более 50)
                contactFilter.setStart(String.valueOf(next));
                // повторяем в цикле, пока на стороне API не останется записей для выдачи нам
                while (next > 0) {
                    totalResult.addResult(result.getResult());
                    result = getBitrixResult(next);
                    if (!Objects.equals(null, result.getNext())) {
                        next = Integer.parseInt(result.getNext());
                    } else {
                        next = 0;
                    }
                }
            }
            // добавляем "хвост" (оставшиеся результаты)
            totalResult.addResult(result.getResult());
        } else {
            logger.error("Error getting contacts info");
        }
        // устанавливаем значение параметра start (т.е. не учитывать в нашей логике)
        contactFilter.setStart("0");
        // возвращаем результат
        return totalResult;
    }
}
