package com.ddkolesnik.bitrixflowsintegration.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * @author Alexandr Stegnin
 */

@Data
@Entity
@NoArgsConstructor
@Table(name = "bitrix_contact")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Contact {

    @Id
    @Column(name = "id")
    @JsonProperty("ID")
    private String id;

    @Column(name = "contact_name")
    @JsonProperty("NAME")
    private String name;

    @Column(name = "contact_second_name")
    @JsonProperty("SECOND_NAME")
    private String secondName;

    @Column(name = "contact_last_name")
    @JsonProperty("LAST_NAME")
    private String lastName;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "contact_id")
    @JsonProperty("EMAIL")
    private List<Email> email;

    @JsonCreator
    public Contact(@JsonProperty("ID") String id,
                   @JsonProperty("NAME") String name,
                   @JsonProperty("SECOND_NAME") String secondName,
                   @JsonProperty("LAST_NAME") String lastName,
                   @JsonProperty("EMAIL") List<Email> email) {
        this.id = id;
        this.name = name;
        this.secondName = secondName;
        this.lastName = lastName;
        this.email = email;
    }

}
