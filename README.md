# Notification Service

Servicio Spring Boot que consume eventos de cambio de estado de pagos desde RabbitMQ y registra evidencia del consumo en los logs.

## Responsabilidad

El servicio recibe eventos `payment.status.changed` de la cola
`notification.payment.status.changed`. Por ahora no persiste ni envía correos o SMS:
su propósito es demostrar comunicación asíncrona real y mantener la implementación pequeña.

## Estructura

```text
application
  dto            contrato del evento que procesa la aplicación
  port/input     caso de uso de notificación
  service        procesamiento de la notificación
adapter/input    consumidor RabbitMQ
infrastructure   configuración técnica
```

## Ejecutar localmente

RabbitMQ debe estar disponible en `localhost:5672` con el usuario `payment_user` y contraseña `payment_pass`.

```powershell
.\mvnw.cmd spring-boot:run
```

El servicio usa el puerto `8085`.

## Configuración

Las siguientes variables son opcionales y tienen valores locales por defecto:

- `RABBITMQ_HOST`
- `RABBITMQ_PORT`
- `RABBITMQ_USER`
- `RABBITMQ_PASSWORD`

## Documentación y monitoreo

- Swagger UI: `http://localhost:8085/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8085/v3/api-docs`
- Estado de salud: `http://localhost:8085/actuator/health`

## Pruebas

```powershell
.\mvnw.cmd test
```

Las pruebas unitarias usan `@Tag("unit")` y las pruebas de integración usan
`@Tag("integration")` con el sufijo `ITest`.
