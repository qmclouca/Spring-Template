package com.qmclouca.base.Dtos;

import com.qmclouca.base.models.Product;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ProductDto extends BaseEntityDto {
    private Double price;
    private String name;
    private String category;
    private String unity;
    private String model;
    private String minQuantity;
    private String physicalState;
    public ProductDto(Product product) {
    }
}
