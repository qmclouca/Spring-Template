package com.qmclouca.base.repositories;

import com.qmclouca.base.models.Client;
import com.qmclouca.base.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer>,ClientRepositoryCustom {


}
