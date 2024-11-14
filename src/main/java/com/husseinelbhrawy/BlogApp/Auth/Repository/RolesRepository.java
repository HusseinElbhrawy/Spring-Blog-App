package com.husseinelbhrawy.BlogApp.Auth.Repository;

import com.husseinelbhrawy.BlogApp.Auth.Entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolesRepository extends JpaRepository<Roles, Long> {

    Optional<Roles> findByName(String name);
}
