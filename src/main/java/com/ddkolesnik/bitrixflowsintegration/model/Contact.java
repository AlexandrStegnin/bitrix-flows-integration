package com.ddkolesnik.bitrixflowsintegration.model;

import lombok.Data;

import java.util.List;

/**
 * @author Alexandr Stegnin
 */

@Data
public class Contact {

    private String id;

    private String name;

    private String secondName;

    private String lastName;

    private List<Email> emails;

}
