package com.ddkolesnik.bitrixflowsintegration.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexandr Stegnin
 * <p>
 * Параметры, которые можно указывать в запросе к API Bitrix, чтобы получать необходимые данные
 * select - список полей, которые хотим получить (ID, Имя, Отчество, Фамилия, список адресов элктронной почты)
 * start - указываем, если API Bitrix отдало ещё не все данные и возвращает параметр next
 * (50 записей за запрос на 29.03.2019)
 */

@Data
public class ContactFilter {

    private List<String> select;
    private String start;

    public ContactFilter() {
        select = new ArrayList<>();
        select.add("ID");
        select.add("NAME");
        select.add("SECOND_NAME");
        select.add("LAST_NAME");
        select.add("EMAIL");
    }

}
