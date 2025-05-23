package com.mclods.store_app.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
@NoArgsConstructor
@Getter
@Setter
public class Category {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Short id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "category")
    @Setter(AccessLevel.NONE)
    List<Product> products = new ArrayList<>();

    public Category(String name) {
        this.name = name;
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
