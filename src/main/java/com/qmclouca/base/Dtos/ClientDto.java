package com.qmclouca.base.Dtos;

import com.qmclouca.base.models.Address;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ClientDto extends BaseDto {

    private String firstName;
    private String middleName;
    private String lastName;
    private LocalDate birthDate;
    private String mobile;
    private String email;
    private List<Address> address;
}
