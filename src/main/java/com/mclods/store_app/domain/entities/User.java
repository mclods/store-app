package com.mclods.store_app.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "user")
    private List<Address> addresses = new ArrayList<>();

    @ManyToMany
    @JoinTable(
        name = "user_tags",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags = new HashSet<>();

    @OneToOne(mappedBy = "user")
    private Profile profile;

    public void addAddress(Address address) {
        address.setUser(this);
        addresses.add(address);
    }

    public void removeAddress(Address address) {
        addresses.remove(address);
        address.setUser(null);
    }

    public void addTag(String tagName) {
        Tag tag = new Tag(tagName);
        tags.add(tag);
        tag.setUser(this);
    }

    public void removeTag(Tag tag) {
        tags.remove(tag);
        tag.setUser(null);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this) {
            return true;
        }

        if(!(obj instanceof User userObj)) {
            return false;
        }

        return (id == null || id.equals(userObj.id)) &&
                name.equals(userObj.name) &&
                email.equals(userObj.email) &&
                password.equals(userObj.password);
    }

    @Override
    public String toString() {
        return String.format("User (id=%d, name=%s, email=%s, " +
                        "password=%s, addresses=%s, tags=%s, profile=%s)",
                id, name, email, password, addresses, tags, profile);
    }
}
