package com.husseinelbhrawy.BlogApp.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "comments" )
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private  long id;

    @Column(name = "body" , nullable = false )
    private  String body;

    @Column(name = "email" , nullable = false )
    private  String email;

    @Column(name = "name" , nullable = false )
    private  String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id" , referencedColumnName = "id" ,nullable = false)
    private  Post post;
}
