package com.qmclouca.base.Dtos;

import jakarta.persistence.MappedSuperclass;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@MappedSuperclass
public class BaseEntityDto {

    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
