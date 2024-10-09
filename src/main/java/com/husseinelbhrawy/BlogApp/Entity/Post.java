package com.husseinelbhrawy.BlogApp.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "posts" , uniqueConstraints = @UniqueConstraint(columnNames = "title"))
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    @Column(name = "id")
    private  long id;
    @Column(name = "title" , nullable = false )
    private  String title;

    @Column(name = "description" , nullable = false )
    private  String description;

    @Column(name = "content" , nullable = false )
    private  String content;

    @OneToMany(mappedBy = "post" , fetch = FetchType.LAZY ,cascade = CascadeType.ALL , orphanRemoval = true)
    //! When Remove Post , we will remove all comments of it
    private Set<Comment> comment = new HashSet<>();

}
