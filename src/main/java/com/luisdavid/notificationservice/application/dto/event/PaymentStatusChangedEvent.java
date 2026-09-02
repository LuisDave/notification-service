package com.luisdavid.notificationservice.application.dto.event;

import java.time.LocalDateTime;
import java.util.UUID;

/** Contrato del evento publicado por payment-service cuando cambia el estado de un pago. */
public record PaymentStatusChangedEvent(
        UUID eventId,
        String eventType,
        String eventVersion,
        LocalDateTime occurredAt,
        Long paymentId,
        String previousStatus,
        String newStatus
) {
}
