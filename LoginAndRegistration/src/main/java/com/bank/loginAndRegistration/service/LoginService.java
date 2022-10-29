package com.bank.loginAndRegistration.service;

import com.bank.apiBankException.LoginFailedException;
import com.bank.loginAndRegistration.dto.UserDto;
import com.bank.loginAndRegistration.entity.User;
import com.bank.loginAndRegistration.jwt.JwtProvider;
import com.bank.loginAndRegistration.mapper.UserMapper;
import com.bank.loginAndRegistration.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoginService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String createToken(String username, String password) throws LoginFailedException {

        if (username == null) throw new LoginFailedException("Campo Username vuoto");

        User user = userRepository.findByUsername(username);

        if (user == null) throw new LoginFailedException("Utente non trovato");

        String role = user.getAuthority().toString();

        if (!this.passwordEncoder.matches(password, user.getPassword()))
            throw new LoginFailedException("Username  e/o password non corrette");


        Map claimMap = new HashMap(0);
        claimMap.put("id", user.getId());
        claimMap.put("authorities", role);

        return JwtProvider.createJwt(username, claimMap);

    }

    public String getRole(String username) {

        User user = userRepository.findByUsername(username);

        UserDto userDto = userMapper.toDto(user);

        return userDto.getAuthority().toString();
    }


}
