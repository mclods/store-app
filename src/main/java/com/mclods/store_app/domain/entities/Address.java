package com.mclods.store_app.domain.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "addresses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "street")
    private String street;

    @Column(name = "city")
    private String city;

    @Column(name = "zip")
    private String zip;

    @Column(name = "state")
    private String state;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public boolean isValid() {
        return street != null && city != null && zip != null && state != null;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }

        if(!(obj instanceof Address addressObj)) {
            return false;
        }

        return id != null && id.equals(addressObj.id);
    }

    @Override
    public String toString() {
        return String.format("Address (id=%d, street=%s, city=%s, zip=%s, state=%s)",
                id, street, city, zip, state);
    }
}
