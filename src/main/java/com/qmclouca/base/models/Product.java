package com.qmclouca.base.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Product extends BaseEntity{
    @Column
    private Double price;
    @Column
    private String name;
    @Column
    private String category;
    @Column
    private String unity;
    @Column
    private String model;
    @Column
    private String minQuantity;
    @Column
    private String physicalState;
}
