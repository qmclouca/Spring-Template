package com.qmclouca.base.Dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class ClientDto extends BaseEntityDto {

    private String clientName;
    private String password;
    private String name;
    private LocalDate birthDate;
    private String mobile;
    private String email;
    private List<AddressDto> address;
}
