package com.ddkolesnik.bitrixflowsintegration.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexandr Stegnin
 */

@Data
@Entity
@NoArgsConstructor
@Table(name = "bitrix_contact")
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Contact extends AbstractEntity {

    @Column(name = "contact_name")
    @JsonProperty("NAME")
    private String name;

    @Column(name = "contact_second_name")
    @JsonProperty("SECOND_NAME")
    private String secondName;

    @Column(name = "contact_last_name")
    @JsonProperty("LAST_NAME")
    private String lastName;

    @OneToMany(mappedBy = "contact", cascade = CascadeType.ALL)
    @JsonProperty("EMAIL")
    private List<Email> emails;

    @Transient
    @JsonProperty("UF_CRM_AMO_413201")
    private String userType;

    @Column(name = "partner_code")
    @JsonProperty("UF_CRM_1568363263537")
    private String partnerCode;

    @JsonCreator
    public Contact(@JsonProperty("ID") String id,
                   @JsonProperty("NAME") String name,
                   @JsonProperty("SECOND_NAME") String secondName,
                   @JsonProperty("LAST_NAME") String lastName,
                   @JsonProperty("EMAIL") List<Email> email,
                   @JsonProperty("UF_CRM_AMO_413201") String userType,
                   @JsonProperty("UF_CRM_1568363263537") String partnerCode) {
        this.setId(id);
        this.name = name;
        this.secondName = secondName;
        this.lastName = lastName;
        this.emails = email;
        this.userType = userType;
        this.partnerCode = partnerCode;
    }

}
