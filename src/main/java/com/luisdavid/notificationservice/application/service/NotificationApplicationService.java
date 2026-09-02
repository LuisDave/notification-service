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
        // Se registra la transición para poder confirmar el consumo mientras no exista un canal de notificación externo.
        LOGGER.info(
                "Evento recibido: pago {} cambió de {} a {}. Event ID: {}",
                event.paymentId(),
                event.previousStatus(),
                event.newStatus(),
                event.eventId()
        );
    }
}
