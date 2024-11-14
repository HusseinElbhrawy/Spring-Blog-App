package com.husseinelbhrawy.BlogApp.Auth.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
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
@Builder
public class User implements UserDetails {

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

    @ManyToMany(fetch = FetchType.EAGER )
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id" ),
            inverseJoinColumns = @JoinColumn(name = "role_id" )
    )
    private Set<Roles> roles ;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(roles -> new SimpleGrantedAuthority(roles.getName().toLowerCase())).toList();
    }
}
