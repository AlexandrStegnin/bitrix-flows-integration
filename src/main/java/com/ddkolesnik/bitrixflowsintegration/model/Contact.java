package com.ddkolesnik.bitrixflowsintegration.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * @author Alexandr Stegnin
 */

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Contact {

    @JsonProperty("ID")
    private String id;

    @JsonProperty("NAME")
    private String name;

    @JsonProperty("SECOND_NAME")
    private String secondName;

    @JsonProperty("LAST_NAME")
    private String lastName;

    @JsonProperty("EMAIL")
    private List<Email> email;

    @JsonCreator
    public Contact(@JsonProperty("ID") String id,
                   @JsonProperty("NAME") String  name,
                   @JsonProperty("SECOND_NAME") String  secondName,
                   @JsonProperty("LAST_NAME") String  lastName,
                   @JsonProperty("EMAIL") List<Email> email) {
        this.id = id;
        this.name = name;
        this.secondName = secondName;
        this.lastName = lastName;
        this.email = email;
    }

}
