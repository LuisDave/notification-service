package com.luisdavid.notificationservice.infrastructure.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** Declara la topología RabbitMQ que consume los eventos emitidos por payment-service. */
@Configuration
public class RabbitMqConfig {

    @Bean
    DirectExchange paymentEventsExchange(@Value("${notification.messaging.exchange}") String exchangeName) {
        return new DirectExchange(exchangeName);
    }

    @Bean
    Queue paymentStatusChangedQueue(@Value("${notification.messaging.payment-status-changed-queue}") String queueName) {
        return new Queue(queueName, true);
    }

    @Bean
    Binding paymentStatusChangedBinding(
            Queue paymentStatusChangedQueue,
            DirectExchange paymentEventsExchange,
            @Value("${notification.messaging.payment-status-changed-routing-key}") String routingKey
    ) {
        return BindingBuilder.bind(paymentStatusChangedQueue)
                .to(paymentEventsExchange)
                .with(routingKey);
    }

    @Bean
    MessageConverter messageConverter() {
        return new JacksonJsonMessageConverter();
    }
}
