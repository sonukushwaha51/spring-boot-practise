package com.labs.mysql.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "user_practise")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "first_name")
    private String firstName;

    private String email;

    private String phone;

    @Column(name = "description")
    private int age;

}
