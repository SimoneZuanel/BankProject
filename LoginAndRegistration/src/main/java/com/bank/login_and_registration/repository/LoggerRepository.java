package com.bank.login_and_registration.repository;

import com.bank.login_and_registration.entity.Logger;
import com.bank.login_and_registration.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LoggerRepository extends JpaRepository<Logger, Integer> {
    @Query(value = "SELECT logger.username FROM Logger logger")
    List<Integer> findUsernames();

    Logger findByUsername(String username);

    Logger findByUserId(User userId);
}
