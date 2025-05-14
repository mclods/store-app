package com.mclods.store_app.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tags")
@Getter
@Setter
@NoArgsConstructor
public class Tag {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Integer id;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "tags")
    private Set<User> users = new HashSet<>();

    public Tag(String name) {
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

        return id != null && id.equals(tagObj.id);
    }

    @Override
    public String toString() {
        return String.format("Tag (id=%d, name=%s)", id, name);
    }
}
