package com.mclods.store_app.domain.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tags")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Tag {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "tags")
    private User user;

    Tag(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this) {
            return true;
        }

        if(!(obj instanceof Tag tagObj)) {
            return false;
        }

        return (id == null || id.equals(tagObj.id)) &&
                name.equals(tagObj.name);
    }

    @Override
    public String toString() {
        return String.format("Tag (id=%d, name=%s)", id, name);
    }
}
