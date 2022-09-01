package com.bank.login_and_registration.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "authorities")
@Data
public class Authority {

    @Id
    @Column(name = "username")
    private String username;

    @Column(name = "authority")
    private AuthorityEnum authority;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "logger_id")
    private Logger loggerId;
}
