package com.ddkolesnik.bitrixflowsintegration.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author Alexandr Stegnin
 */

@Data
@Entity
@NoArgsConstructor
@Table(name = "bitrix_contact_email")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Email {

    @Id
    @Column(name = "id")
    @JsonProperty("ID")
    private String id;

    @Column(name = "value_type")
    @JsonProperty("VALUE_TYPE")
    private String valueType;

    @Column(name = "value")
    @JsonProperty("VALUE")
    private String value;

    @Column(name = "type_id")
    @JsonProperty("TYPE_ID")
    private String typeId;

    @JsonIgnore
    @ManyToOne
    private Contact contact;

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
