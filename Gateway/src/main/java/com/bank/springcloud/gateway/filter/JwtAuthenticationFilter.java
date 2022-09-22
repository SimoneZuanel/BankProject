package com.bank.springcloud.gateway.filter;

import com.bank.springcloud.gateway.exception.JwtTokenMalformedException;
import com.bank.springcloud.gateway.exception.JwtTokenMissingException;
import com.bank.springcloud.gateway.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Component
public class JwtAuthenticationFilter implements GatewayFilter {

    @Autowired
    private JwtUtil jwtUtil;

    private ArrayList<String> stringArrayList;

    private String urlToCheck = "";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        final List<String> apiEndpoints = List.of("/api/login/login", "/api/registration/registration");

        Predicate<ServerHttpRequest> isApiSecured = r -> apiEndpoints.stream()
                .noneMatch(uri -> r.getURI().getPath().contains(uri));

        if (isApiSecured.test(request)) {
            if (!request.getHeaders().containsKey("Authorization")) {
                ServerHttpResponse response = exchange.getResponse();
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return response.setComplete();
            }

            final String token = request.getHeaders().getOrEmpty("Authorization").get(0).substring(7);

            try {
                jwtUtil.validateToken(token);
            } catch (JwtTokenMalformedException | JwtTokenMissingException e) {

                ServerHttpResponse response = exchange.getResponse();
                response.setStatusCode(HttpStatus.BAD_REQUEST);

                return response.setComplete();
            }

            urlToCheck = request.getURI().toString();

            String numberToPass = "";
            for (int i = 21; i < urlToCheck.length(); i++) {
                if (urlToCheck.charAt(i) >= 48 && urlToCheck.charAt(i) <= 57) {
                    numberToPass += urlToCheck.charAt(i);
                    if (i < urlToCheck.length() - 1) {
                        if (urlToCheck.charAt(i + 1) < 48 || urlToCheck.charAt(i + 1) > 57) {
                            numberToPass += ",";
                        }
                    }
                }
            }

            stringArrayList = new ArrayList<>(List.of(numberToPass.split(",")));

            Claims claims = jwtUtil.getClaims(token);

            final List<String> apiEndpointsEmployee = List.of(

                    "/api/account/getBankAccounts",

                    "/api/account/openFirstBankAccount",

                    "/api/account/openAnotherBankAccount",

                    "/api/account/closeBankAccount/" + stringArrayList.get(0),

                    "/api/print/getAllUsers"

                    );

            final List<String> apiEndpointsClient = List.of(

                    "/api/account/" + stringArrayList.get(0),

                    "/api/account/openingRequestBankAccount",

                    "/api/account/closingRequestBankAccount",

                    "/api/print/getLast10Transactions",

                    "/api/print/getAllTransactions",

                    "/api/transaction/withdrawal",

                    "/api/transaction/deposit",

                    "/api/transaction/bankTransfer"

                   );

            isApiSecured = r -> apiEndpointsClient.stream()
                    .anyMatch(uri -> r.getURI().getPath().contains(uri));

            //throughout a loop a check is made to deduce that the URI exists in an endPoint permitted for the client
            for (String s : apiEndpointsClient) {
                String control = "http://localhost:8083" + s;
                if (control.equals(urlToCheck)) {
                   if (isApiSecured.test(request)) {
                        if (!((String) claims.get("authorities")).contains("ROLE_CLIENT")) {
                            ServerHttpResponse response = exchange.getResponse();
                            response.setStatusCode(HttpStatus.UNAUTHORIZED);
                            return response.setComplete();
                        }
                   }
                    exchange.getRequest().mutate().header("id", String.valueOf(claims.get("id"))).build();
                    break;
                }
            }

            isApiSecured = r -> apiEndpointsEmployee.stream()
                    .anyMatch(uri -> r.getURI().getPath().contains(uri));

            //throughout a loop a check is made to deduce that the URI exists in an endPoint permitted for the employee
            for (String s : apiEndpointsEmployee) {
                String control = "http://localhost:8083" + s;
                if (control.equals(urlToCheck)) {
                   if (isApiSecured.test(request)) {
                        if (!((String) claims.get("authorities")).contains("ROLE_EMPLOYEE")) {
                            ServerHttpResponse response = exchange.getResponse();
                            response.setStatusCode(HttpStatus.UNAUTHORIZED);
                            return response.setComplete();
                        }
                   }
                    exchange.getRequest().mutate().header("id", String.valueOf(claims.get("id"))).build();
                    break;
                }
            }

        }

        return chain.filter(exchange);
    }

}