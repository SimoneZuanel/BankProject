package com.bank.loginAndRegistration.repository;

import com.bank.loginAndRegistration.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorityRepository extends JpaRepository<Authority, Integer> {

    List<Authority> findAllByUsername(String username);
}
