package com.qmclouca.base.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
public class Client extends BaseEntity{

    @Column
    private String clientName;
    @Column
    private String password;
    @Column
    private String name;
    @Column
    private LocalDate birthDate;
    @Column
    private String mobile;
    @Column
    private String email;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id")
    private List<Address> address;
}
