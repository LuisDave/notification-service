# Notification Service

> Incluye una colección Postman en `postman/notification-service.postman_collection.json`.

Consumidor asíncrono de los cambios de estado de pago. Su responsabilidad actual es recibir el evento desde RabbitMQ y dejar evidencia del consumo en los logs; no envía correos/SMS ni administra una base de datos.

## Especificación técnica

| Aspecto | Valor |
| --- | --- |
| Runtime | Java 21 / Spring Boot 4.1.1 |
| Puerto HTTP | `8085` |
| Entrada de negocio | RabbitMQ |
| Exchange | `payment.events` (direct) |
| Routing key | `payment.status.changed` |
| Cola durable | `notification.payment.status.changed` |
| Documentación | Swagger `http://localhost:8085/swagger-ui.html` |
| Observabilidad | `GET /actuator/health` |

## Flujo de mensajes

1. `payment-service` confirma un cambio de estado en MySQL.
2. Publica el evento JSON `payment.status.changed` en RabbitMQ.
3. RabbitMQ lo enruta a `notification.payment.status.changed`.
4. `PaymentStatusChangedListener` lo deserializa y lo entrega al caso de uso.
5. El servicio registra el pago, los estados y el ID del evento en los logs.

Contrato del evento:

```json
{
  "eventId": "uuid",
  "eventType": "payment.status.changed",
  "eventVersion": "1",
  "occurredAt": "2026-09-01T22:00:00",
  "paymentId": 1,
  "previousStatus": "PENDING",
  "newStatus": "PROCESSING"
}
```

## Configuración

| Variable | Valor local predeterminado |
| --- | --- |
| `RABBITMQ_HOST` | `localhost` |
| `RABBITMQ_PORT` | `5672` |
| `RABBITMQ_USER` / `RABBITMQ_PASSWORD` | `payment_user` / `payment_pass` |

## Ejecutar y verificar

Para el flujo completo, sigue el manual de [orchestrator](https://github.com/LuisDave/orchestrator). Desde la carpeta `orchestrator`:

```powershell
docker compose up --build -d
docker compose logs -f notification-service
```

Ejecuta el happy path documentado en `payment-service`. Por cada transición válida aparecerá un registro similar a:

```text
Evento recibido: pago 1 cambió de PENDING a PROCESSING. Event ID: <uuid>
```

Puedes inspeccionar la cola desde RabbitMQ UI: `http://localhost:15672`, con `payment_user` / `payment_pass`.

El `Dockerfile` construye el JAR con Maven y lo ejecuta con JRE 21. La orquestación completa vive en el repositorio [orchestrator](https://github.com/LuisDave/orchestrator).

### Pasos locales

1. Inicia RabbitMQ desde `payment-service` con `docker compose up -d`, o levanta todo desde [orchestrator](https://github.com/LuisDave/orchestrator).
2. Confirma que RabbitMQ responde en `http://localhost:15672`.
3. Desde `notification-service`, ejecuta `.\mvnw.cmd spring-boot:run`.
4. Comprueba `http://localhost:8085/actuator/health`.
5. Ejecuta el happy path de `payment-service` y observa `Evento recibido` en los logs.

### Ejecutar el Dockerfile

Con RabbitMQ local disponible, construye y ejecuta la imagen:

```powershell
docker build -t notification-service:local .
docker run --rm --name notification-service -p 8085:8085 `
  -e RABBITMQ_HOST=host.docker.internal -e RABBITMQ_PORT=5672 `
  -e RABBITMQ_USER=payment_user -e RABBITMQ_PASSWORD=payment_pass notification-service:local
```

Para evitar configurar manualmente esas variables, desde la carpeta de [orchestrator](https://github.com/LuisDave/orchestrator) usa `docker compose up --build -d notification-service`.

## Persistencia, pruebas y Postman

Este servicio no tiene migraciones SQL porque no posee datos. El comportamiento de negocio se recibe exclusivamente por RabbitMQ. Importa `postman/notification-service.postman_collection.json` y ejecuta **Health** y **OpenAPI document** para verificar la API. Para provocar un evento de negocio, sigue el happy path de la colección de `payment-service`; el resultado se comprueba en los logs de este servicio.

```powershell
.\mvnw.cmd test
```
