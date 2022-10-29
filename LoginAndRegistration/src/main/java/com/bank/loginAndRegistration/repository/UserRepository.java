package com.bank.loginAndRegistration.repository;

import com.bank.loginAndRegistration.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);

    List<User> findAllByOrderByFirstNameAscLastNameAsc();

    @Query(value = "SELECT user.username FROM User user")
    List<String> findUsernames();

    User findByUsername(String username);

}
