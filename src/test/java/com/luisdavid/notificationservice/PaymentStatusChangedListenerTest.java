package com.luisdavid.notificationservice;

import com.luisdavid.notificationservice.adapter.input.messaging.PaymentStatusChangedListener;
import com.luisdavid.notificationservice.application.dto.event.PaymentStatusChangedEvent;
import com.luisdavid.notificationservice.application.port.input.INotificationUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@Tag("unit")
@DisplayName("Pruebas unitarias de PaymentStatusChangedListener")
class PaymentStatusChangedListenerTest {

    @Mock
    private INotificationUseCase notificationUseCase;

    @Test
    @DisplayName("Debe delegar el evento recibido al caso de uso de notificación")
    void shouldDelegateReceivedEventToNotificationUseCase() {
        PaymentStatusChangedEvent event = new PaymentStatusChangedEvent(
                UUID.randomUUID(),
                "payment.status.changed",
                "1",
                LocalDateTime.now(),
                25L,
                "PENDING",
                "PROCESSING"
        );
        PaymentStatusChangedListener listener = new PaymentStatusChangedListener(notificationUseCase);

        listener.consumePaymentStatusChangedEvent(event);

        verify(notificationUseCase).processPaymentStatusChangedEvent(event);
    }
}
