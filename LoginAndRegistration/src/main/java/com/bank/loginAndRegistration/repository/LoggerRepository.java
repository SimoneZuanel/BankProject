package com.bank.loginAndRegistration.repository;

import com.bank.loginAndRegistration.entity.Logger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LoggerRepository extends JpaRepository<Logger, Integer> {
    @Query(value = "SELECT logger.username FROM Logger logger")
    List<Integer> findUsernames();

    Logger findByUsername(String username);

}
