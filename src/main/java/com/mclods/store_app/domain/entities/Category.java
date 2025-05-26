package com.mclods.store_app.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
public class Category {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "category")
    @Setter(AccessLevel.NONE)
    List<Product> products = new ArrayList<>();

    public Category(Short id, String name, List<Product> products) {
        this.id = id;
        this.name = name;

        if(products != null) {
            for(Product product : products) {
                addProduct(product);
            }
        }
    }

    public void addProduct(Product product) {
        product.setCategory(this);
        products.add(product);
    }

    public void removeProduct(Product product) {
        product.setCategory(null);
        products.remove(product);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this) {
            return true;
        }

        if(!(obj instanceof Category categoryObj)) {
            return false;
        }

        return id != null && id.equals(categoryObj.id);
    }

    @Override
    public String toString() {
        return String.format("Category (id=%d, name=%s)", id, name);
    }
}
