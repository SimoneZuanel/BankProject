package com.bank.springcloud.gateway.config;

import com.bank.springcloud.gateway.filter.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class GatewayConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationFilter filter;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes().route("login", r -> r.path("/api/login/**").filters(f -> f.filter(filter)).uri("lb://logAndReg"))
                .route("registration", r -> r.path("/api/registration/**").filters(f -> f.filter(filter)).uri("lb://logAndReg"))
                .route("account", r -> r.path("/api/account/**").filters(f -> f.filter(filter)).uri("lb://account"))
                .route("print", r -> r.path("/api/print/**").filters(f -> f.filter(filter)).uri("lb://operation"))
                .route("transaction", r -> r.path("/api/transaction/**").filters(f -> f.filter(filter)).uri("lb://operation")).build();
    }

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http.csrf(ServerHttpSecurity.CsrfSpec::disable);
        return http.build();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}

