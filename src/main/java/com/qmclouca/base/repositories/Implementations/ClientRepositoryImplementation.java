package com.qmclouca.base.repositories.Implementations;

import com.qmclouca.base.models.Client;
import com.qmclouca.base.repositories.ClientRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Repository
public class ClientRepositoryImplementation implements ClientRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Client> getClientsByName(String name) {
        String query = "SELECT c FROM Client c WHERE LOWER(c.name) LIKE CONCAT('%', LOWER(:name), '%')";
        return entityManager.createQuery(query, Client.class)
                .setParameter("name", name)
                .getResultList();
    }

    public Optional<Client> getClientByName(String name){
        String query = "SELECT c FROM Client c WHERE LOWER(c.name) = LOWER(:name)";

        List<Client> result = entityManager.createQuery(query, Client.class)
                .setParameter("name", name)
                .setMaxResults(1)
                .getResultList();
        if (result != null){
            return Optional.of(result.get(0));
        } else {
            return Optional.empty();
        }
    }

    public Optional<Client> getClientById(Long id) {
        String query = "SELECT c FROM Client c WHERE c.id = :id"; // Use 'c.id' instead of 'c.client_id'
        TypedQuery<Client> typedQuery = entityManager.createQuery(query, Client.class)
                .setParameter("id", id);

        try {
            Client result = typedQuery.getSingleResult();
            return Optional.of(result);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Client> findByClientNameAndPassword(String userName, String password) {
        String query = "SELECT c FROM Client c WHERE c.clientName = :userName AND c.password = :password"; // Use 'c.id' instead of 'c.client_id'
        TypedQuery<Client> typedQuery = entityManager.createQuery(query, Client.class)
                .setParameter("userName", userName)
                .setParameter("password", password);
        try {
            Client result = typedQuery.getSingleResult();
            return Optional.of(result);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public void delete(Client entity) {

    }
    @Override
    public void flush() {

    }

    @Override
    public <S extends Client> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Client> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Client> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Integer> integers) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Client getOne(Integer integer) {
        return null;
    }

    @Override
    public Client getById(Integer integer) {
        return null;
    }

    @Override
    public Client getReferenceById(Integer integer) {
        return null;
    }

    @Override
    public <S extends Client> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Client> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Client> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public List<Client> findAll() {
        return null;
    }

    @Override
    public List<Client> findAllById(Iterable<Integer> integers) {
        return null;
    }

    @Override
    public <S extends Client> S save(S entity) {
        return null;
    }

    @Override
    public Optional<Client> findById(Integer integer) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Integer integer) {

    }



    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {

    }

    @Override
    public void deleteAll(Iterable<? extends Client> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Client> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Client> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Client> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Client> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Client> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Client> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Client, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
}
