package com.mclods.store_app.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "profiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Profile {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "bio")
    private String bio;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "loyalty_points")
    private Integer loyaltyPoints;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    @MapsId
    private User user;

    @Override
    public boolean equals(Object obj) {
        if(obj == this) {
            return true;
        }

        if(!(obj instanceof Profile profileObj)) {
            return false;
        }

        return id != null && id.equals(profileObj.id);
    }

    @Override
    public String toString() {
        return String.format("Profile (id=%d, bio=%s, phoneNumber=%s, " +
                        "dateOfBirth=%s, loyaltyPoints=%d)",
                id, bio, phoneNumber, dateOfBirth, loyaltyPoints);
    }
}
