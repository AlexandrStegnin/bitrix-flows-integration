package com.ddkolesnik.bitrixflowsintegration.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Alexandr Stegnin
 * <p>
 * Структура ответа API Bitrix
 */

@Data
public class BitrixResult {

    private List<Contact> result;

    private String error;

    private String total;

    private String next;

    public void addResult(List<Contact> res) {
        if (Objects.equals(null, result)) result = new ArrayList<>();
        result.addAll(res);
    }

}
