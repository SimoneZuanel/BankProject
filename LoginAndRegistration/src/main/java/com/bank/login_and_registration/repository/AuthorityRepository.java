package com.bank.login_and_registration.repository;

import com.bank.login_and_registration.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Integer> {
}
