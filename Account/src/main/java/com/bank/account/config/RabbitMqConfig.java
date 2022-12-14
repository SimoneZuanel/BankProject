package com.bank.account.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
    @Bean
    public Queue newAccount() {
        return new Queue("newAccount", false);
    }

    @Bean
    public Queue withdrawal() {
        return new Queue("withdrawal", false);
    }

    @Bean
    public Queue deposit() {
        return new Queue("deposit", false);
    }

    @Bean
    public Queue bankTransfer() { return new Queue("bankTransfer", false); }

    @Bean
    public Queue ibanList() { return new Queue("ibanList", false); }

    @Bean
    public Queue sendUser() {
        return new Queue("sendUser", false);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
