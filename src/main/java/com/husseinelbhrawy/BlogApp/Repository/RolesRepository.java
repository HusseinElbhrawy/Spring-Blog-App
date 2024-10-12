package com.husseinelbhrawy.BlogApp.Repository;

import com.husseinelbhrawy.BlogApp.Entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolesRepository extends JpaRepository<Roles, Long> {

    Optional<Roles> findByName(String name);
}
