package com.luisdavid.notificationservice.application.service;

import com.luisdavid.notificationservice.application.dto.event.PaymentStatusChangedEvent;
import com.luisdavid.notificationservice.application.port.input.INotificationUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/** Procesa los eventos recibidos y deja evidencia de la notificación consumida. */
@Service
public class NotificationApplicationService implements INotificationUseCase {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationApplicationService.class);

    @Override
    public void processPaymentStatusChangedEvent(PaymentStatusChangedEvent event) {
        // La evidencia en logs permite demostrar el consumo asíncrono sin añadir persistencia innecesaria.
        LOGGER.info(
                "Evento recibido: pago {} cambió de {} a {}. Event ID: {}",
                event.paymentId(),
                event.previousStatus(),
                event.newStatus(),
                event.eventId()
        );
    }
}
