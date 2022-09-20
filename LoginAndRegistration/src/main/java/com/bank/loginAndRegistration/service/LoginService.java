package com.bank.loginAndRegistration.service;

import com.bank.apiBankException.LoginFailedException;
import com.bank.loginAndRegistration.dto.AuthorityDto;
import com.bank.loginAndRegistration.entity.Authority;
import com.bank.loginAndRegistration.entity.Logger;
import com.bank.loginAndRegistration.jwt.JwtProvider;
import com.bank.loginAndRegistration.mapper.AuthorityMapper;
import com.bank.loginAndRegistration.repository.AuthorityRepository;
import com.bank.loginAndRegistration.repository.LoggerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class LoginService {

    @Autowired
    private LoggerRepository loggerRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private AuthorityMapper authorityMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String createToken(String username, String password) throws LoginFailedException {

        if (username == null) throw new LoginFailedException("Campo Username vuoto");

        Logger logger = loggerRepository.findByUsername(username);

        if (logger == null) throw new LoginFailedException("Utente non trovato");

        List<Authority> authorityList = authorityRepository.findAllByUsername(username);

        ArrayList<String> roles = new ArrayList<>();

        for (Authority authority : authorityList) {
            roles.add(authority.getAuthority().toString());
        }

        if (!this.passwordEncoder.matches(password, logger.getPassword()))
            throw new LoginFailedException("Username  e/o password non corrette");


        Map claimMap = new HashMap(0);
        claimMap.put("id", logger.getId());
        claimMap.put("authorities", roles.stream().collect(Collectors.joining(",")));

        return JwtProvider.createJwt(username, claimMap);

    }

    public List<String> getRoles(String username) {

        List<Authority> authorityList = authorityRepository.findAllByUsername(username);

        List<String> authorityDtoList = new ArrayList<>();

        for (Authority authority : authorityList) {
            AuthorityDto authorityDto = authorityMapper.toDto(authority);
            authorityDtoList.add(authorityDto.getAuthority().toString());
        }

        return authorityDtoList;
    }


}
