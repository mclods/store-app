package com.mclods.store_app.repositories;

import com.mclods.store_app.domain.entities.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    @EntityGraph(attributePaths = {"addresses", "profile"})
    @Query("SELECT u FROM User u WHERE u.id = :id")
    Optional<User> findUser(Long id);

    // The default findAll provided by CrudRepository leads to N+1 problem
    // to avoid it we use @EntityGraph to fetch addresses and profile eagerly
    @EntityGraph(attributePaths = {"addresses", "profile"})
    @Query("SELECT u FROM User u")
    List<User> findAllUsers();
}
