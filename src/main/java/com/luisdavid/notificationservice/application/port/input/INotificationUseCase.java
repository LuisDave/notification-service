package com.luisdavid.notificationservice.application.port.input;

import com.luisdavid.notificationservice.application.dto.event.PaymentStatusChangedEvent;

/** Define el caso de uso ejecutado al recibir un cambio de estado de pago. */
public interface INotificationUseCase {

    void processPaymentStatusChangedEvent(PaymentStatusChangedEvent event);
}
