package com.qmclouca.base.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity
public class Address extends BaseEntity {
    @Column
    private String street;
    @Column
    private String city;
    @Column
    private String state;
    @Column
    private String postalCode;
    @Column
    private String number;
    @Column
    private String references;
}
