package com.husseinelbhrawy.BlogApp.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "categories" , uniqueConstraints = @UniqueConstraint(columnNames = "title"))

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private  long id;
    @Column(name = "title" , nullable = false  , unique = true)
    private  String name;
    private  String description;

    @OneToMany(mappedBy = "category" , cascade = CascadeType.ALL , fetch = FetchType.LAZY)
//    , orphanRemoval = true
    private Set<Post> posts;
}
