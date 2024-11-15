package com.husseinelbhrawy.BlogApp.Auth.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "roles", uniqueConstraints = {
    @UniqueConstraint(columnNames = "name" ),
})

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Roles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name" , nullable = false  , unique = true)
    private String name;

}
