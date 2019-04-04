package com.ddkolesnik.bitrixflowsintegration.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Alexandr Stegnin
 * <p>
 * Параметры, которые можно указывать в запросе к API Bitrix, чтобы получать необходимые данные
 * select - список полей, которые хотим получить (ID, Имя, Отчество, Фамилия, список адресов элктронной почты, Вид пользователя)
 * start - указываем, если API Bitrix отдало ещё не все данные и возвращает параметр next
 * (50 записей за запрос на 29.03.2019)
 * params - id значения пользовательского поля "Вид пользователя", которое отвечает за признак "Инвестор"
 * filter - для передачи списка параметров с id в Bitrix
 */

@Data
public class ContactFilter {

    private static final String USER_FIELD_NAME = "UF_CRM_AMO_413201";
    private List<String> select = new ArrayList<>();
    private String start = "0";
    private String[] params;
    private Map<String, String[]> filter = new HashMap<>(1);

    public ContactFilter() {
        select.add("ID");
        select.add("NAME");
        select.add("SECOND_NAME");
        select.add("LAST_NAME");
        select.add("EMAIL");
        select.add("UF_CRM_AMO_413201");
        params = new String[]{"44"};
        filter.put(USER_FIELD_NAME, params);
    }

}
