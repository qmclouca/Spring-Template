package com.qmclouca.base.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
public class Client extends BaseEntity{

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
