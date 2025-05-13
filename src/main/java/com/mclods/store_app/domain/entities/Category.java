package com.mclods.store_app.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Category {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "category")
    List<Product> products = new ArrayList<>();

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
