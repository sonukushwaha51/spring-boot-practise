package com.labs.postgres.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "book_practise")
@IdClass(BookId.class)
@NoArgsConstructor
public class Book {

    @Id
    @Column(name = "book_id")
    private int bookId = UUID.randomUUID().hashCode();

    @Column(name = "book_name")
    private String bookName;

    @Column(name = "author_name")
    @Id
    private String authorName;

    @Column(name = "description")
    private String description;

}
