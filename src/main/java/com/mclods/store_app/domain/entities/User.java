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
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "user")
    @Setter(AccessLevel.NONE)
    private List<Address> addresses = new ArrayList<>();

    @ManyToMany
    @JoinTable(
        name = "user_tags",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    @Setter(AccessLevel.NONE)
    private Set<Tag> tags = new HashSet<>();

    @OneToOne(mappedBy = "user", cascade = { CascadeType.PERSIST, CascadeType.REMOVE})
    @Setter(AccessLevel.NONE)
    private Profile profile;

    @ManyToMany
    @JoinTable(
            name = "wishlist",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    @Setter(AccessLevel.NONE)
    private Set<Product> wishlist = new HashSet<>();

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public void addAddress(Address address) {
        address.setUser(this);
        addresses.add(address);
    }

    public void removeAddress(Address address) {
        address.setUser(null);
        addresses.remove(address);
    }

    public void addProfile(Profile profile) {
        profile.setUser(this);
        this.profile = profile;
    }

    public void removeProfile(Profile profile) {
        profile.setUser(null);
        this.profile = null;
    }

    public void addToWishlist(Product product) {
        wishlist.add(product);
    }

    public void removeFromWishlist(Product product) {
        wishlist.remove(product);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this) {
            return true;
        }

        if(!(obj instanceof User userObj)) {
            return false;
        }

        return id != null && id.equals(userObj.id);
    }

    @Override
    public String toString() {
        return String.format("User (id=%d, name=%s, email=%s, " +
                        "password=%s, addresses=%s, tags=%s, profile=%s, wishlist=%s)",
                id, name, email, password, addresses, tags, profile, wishlist);
    }
}
