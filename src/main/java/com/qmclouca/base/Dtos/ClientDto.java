package com.qmclouca.base.Dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class ClientDto extends BaseEntityDto {

    private String firstName;
    private String middleName;
    private String lastName;
    private LocalDate birthDate;
    private String mobile;
    private String email;
    private List<AddressDto> address;
}
