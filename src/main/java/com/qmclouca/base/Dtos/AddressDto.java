package com.qmclouca.base.Dtos;

import lombok.Data;

@Data
public class AddressDto {
    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String number;
    private String references;
}
