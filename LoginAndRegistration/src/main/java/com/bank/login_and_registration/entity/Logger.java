package com.bank.login_and_registration.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "loggers")
@Data
public class Logger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User userId;
}
