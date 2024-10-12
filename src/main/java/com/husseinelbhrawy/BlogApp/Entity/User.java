package com.husseinelbhrawy.BlogApp.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users" ,uniqueConstraints = {
    @UniqueConstraint(columnNames = "email"),
    @UniqueConstraint(columnNames = "username"),
})

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name" , nullable = false  , unique = true)
    private String name;

    @Column(name = "email" , nullable = false  , unique = true)
    private String email;

    @Column(name = "password" , nullable = false)
    private String password;

    @Column(name = "username" , nullable = false , unique = true)
    private String username;

    @ManyToMany(fetch = FetchType.EAGER ) //! The default was lazy and this is wrong , because i need to load user data and it roles
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id" ),
            inverseJoinColumns = @JoinColumn(name = "role_id" )
    )
    private Set<Roles> roles ;
}
