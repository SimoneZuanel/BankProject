package com.bank.loginAndRegistration.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "authorities")
@Data
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "authority")
    private AuthorityEnum authority;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "logger_id")
    private Logger loggerId;
}
