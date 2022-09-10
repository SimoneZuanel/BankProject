package com.bank.login_and_registration.repository;

import com.bank.login_and_registration.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);

    List<User> findAllByOrderByFirstNameAscLastNameAsc();
}
