package com.qmclouca.base.configs;

import com.qmclouca.base.utils.annotations.Development;
import com.qmclouca.base.utils.annotations.Homologation;
import com.qmclouca.base.utils.annotations.Production;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BaseConfiguration {

    @Bean
    @Development
    public CommandLineRunner executeDev(){
        return args -> System.out.println("Rodando em DEV");
    }
    @Bean
    @Homologation
    public CommandLineRunner executeHTML(){
        return args -> System.out.println("Rodando em HML");
    }
    @Bean
    @Production
    public CommandLineRunner executePRD(){
        return args -> System.out.println("Rodando em PRD");
    }

}