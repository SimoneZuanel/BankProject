package com.bank.login_and_registration.service;

import com.bank.login_and_registration.entity.Logger;
import com.bank.login_and_registration.jwt.JwtProvider;
import com.bank.login_and_registration.repository.LoggerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoginService {

    @Autowired
    private LoggerRepository loggerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String login(String username, String password) {

        if (username == null) throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Campo username vuoto");

        Logger logger = loggerRepository.findByUsername(username);

        if (logger == null) throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Utente non trovato");

        if (!this.passwordEncoder.matches(password, logger.getPassword()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "La password inserita non è corretta");

        ObjectNode loggerNode = new ObjectMapper().convertValue(logger, ObjectNode.class);
        loggerNode.remove("password");
        Map claimMap = new HashMap(0);
        claimMap.put("logger", loggerNode);

        return JwtProvider.createJwt(username, claimMap);

    }

}
