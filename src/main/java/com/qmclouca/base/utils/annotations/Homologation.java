package com.qmclouca.base.utils.annotations;

import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Configuration;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Configuration
@Profile("homologation")
public @interface Homologation {

}