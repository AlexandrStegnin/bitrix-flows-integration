package com.ddkolesnik.bitrixflowsintegration.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexandr Stegnin
 */

@Data
public class ContactFilter {

    private List<String> select;

    public ContactFilter() {
        select = new ArrayList<>();
        select.add("ID");
        select.add("NAME");
        select.add("SECOND_NAME");
        select.add("LAST_NAME");
        select.add("EMAIL");
    }

}
