package com.ddkolesnik.bitrixflowsintegration.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author Alexandr Stegnin
 */

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Email {

    @JsonProperty("ID")
    private String id;

    @JsonProperty("VALUE_TYPE")
    private String valueType;

    @JsonProperty("VALUE")
    private String value;

    @JsonProperty("TYPE_ID")
    private String typeId;

    @JsonCreator
    public Email(@JsonProperty("ID") String id,
                 @JsonProperty("VALUE_TYPE") String valueType,
                 @JsonProperty("VALUE") String value,
                 @JsonProperty("TYPE_ID") String typeId) {
        this.id = id;
        this.valueType = valueType;
        this.value = value;
        this.typeId = typeId;
    }

}
