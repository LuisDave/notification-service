package com.luisdavid.notificationservice.adapter.input.messaging;

import com.luisdavid.notificationservice.application.dto.event.PaymentStatusChangedEvent;
import com.luisdavid.notificationservice.application.port.input.INotificationUseCase;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/** Adaptador de entrada que recibe eventos RabbitMQ y ejecuta el caso de uso de notificación. */
@Component
public class PaymentStatusChangedListener {

    private final INotificationUseCase notificationUseCase;

    public PaymentStatusChangedListener(INotificationUseCase notificationUseCase) {
        this.notificationUseCase = notificationUseCase;
    }

    @RabbitListener(queues = "${notification.messaging.payment-status-changed-queue}")
    public void consumePaymentStatusChangedEvent(PaymentStatusChangedEvent event) {
        notificationUseCase.processPaymentStatusChangedEvent(event);
    }
}
