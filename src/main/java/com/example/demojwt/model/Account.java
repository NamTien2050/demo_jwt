package com.example.demojwt.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
//@Table(name = "user")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String username;
    private String password;
    private String fullName;
    @ManyToMany
    private List<Role> role;
}
