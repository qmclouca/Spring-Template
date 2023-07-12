package com.qmclouca.base.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@MappedSuperclass
public class BaseEntity {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "LONG", updatable = false, nullable = false)
    private Long id;

    @GeneratedValue (strategy = GenerationType.AUTO)
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime modifiedAt;

    @PrePersist
    public void prePersist(){
        this.createdAt = LocalDateTime.now();
    }
}
