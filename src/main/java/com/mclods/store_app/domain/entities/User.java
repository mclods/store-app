package com.mclods.store_app.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
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

    @OneToMany(mappedBy = "user",
            cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE },
            orphanRemoval = true)
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

    @OneToOne(mappedBy = "user",
            cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE },
            orphanRemoval = true)
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

    public User(Long id, String name, String email, String password,
                List<Address> addresses, Set<Tag> tags, Profile profile, Set<Product> wishlist) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;

        if(addresses != null) {
            for(Address address : addresses) {
                addAddress(address);
            }
        }

        if(tags != null) {
            this.tags = tags;
        }

        this.profile = profile;

        if(wishlist != null) {
            for (Product product : wishlist) {
                addToWishlist(product);
            }
        }
    }

    public void addAddress(Address address) {
        address.setUser(this);
        addresses.add(address);
    }

    public void removeAddress(Address address) {
        address.setUser(null);
        addresses.remove(address);
    }

    public Optional<Address> findAddress(Address address) {
        Address foundAddress = null;
        for(Address userAddress : addresses) {
            if(userAddress.equals(address)) {
                foundAddress = userAddress;
                break;
            }
        }

        return Optional.ofNullable(foundAddress);
    }

    public void addProfile(Profile profile) {
        profile.setUser(this);
        this.profile = profile;
    }

    public void removeProfile() {
        profile.setUser(null);
        profile = null;
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
