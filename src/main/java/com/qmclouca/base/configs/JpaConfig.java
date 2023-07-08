package com.qmclouca.base.configs;

import jakarta.persistence.spi.PersistenceProviderResolverHolder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.spi.PersistenceProvider;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories("com.qmclouca.base.repositories")
public class JpaConfig {
    @Bean (name = "entityManagerFactory")
    public EntityManagerFactory entityManagerFactoryBean(DataSource dataSource){
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("com.qmclouca.base.models");

        PersistenceProvider persistenceProvider = PersistenceProviderResolverHolder
                .getPersistenceProviderResolver()
                .getPersistenceProviders()
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No persistence providers found."));

        em.setPersistenceProvider(persistenceProvider);
        return em.getObject();
    }
}
