package com.mclods.store_app.domain.dtos.product;

import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class ProductSummary {
    private Long id;

    private String name;

    private String description;

    private Double price;

    private String category;
}
