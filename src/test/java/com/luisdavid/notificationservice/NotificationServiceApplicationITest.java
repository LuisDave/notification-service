package com.luisdavid.notificationservice;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Tag("integration")
@DisplayName("Prueba de integración del contexto de Notification Service")
class NotificationServiceApplicationITest {

    @Test
    @DisplayName("Debe iniciar el contexto de Spring y conectarse a RabbitMQ")
    void shouldLoadApplicationContext() {
    }

}
