# Order-Service

## Ansvar
Order-Service sköter webshoppens ordrar. När en kund lägger en order sparas den i databasen och product-service anropas, sedan minskar lagersaldot för beställda produkter och orderbekräftelsen skickas till email-service.

## Tekniker
Java 17, Spring Boot, Maven, Lombok, Spring Web, SpringDoc OpenAPI, Spring security med OAuth2 resource server, Spring data JPA, RabbitMQ.

## Databas
PostgreSQL på Neon. H2 används för tester.

## Port
7500 

## Miljövariabler
- DB_PASSWORD, DB_URL, DB_USERNAME = Databas
- JWT_KEY_ID, JWT_PRIVATE_KEY, JWT_PUBLIC_KEY = Nycklar för jwt
- RABBITMQ_EMAIL_QUEUE = Kön alla ordrar skickas till
- RABBITMQ_HOST, RABBITMQ_PASSWORD, RABBITMQ_PORT, RABBITMQ_USERNAME och RABBITMQ_VIRTUAL_HOST = RabbitMQ anslutning.

## Hur webshoppen fungerar
- **Webshop-frontend** – Användarvy och anropar backend via gateway-service.
- **Gateway-service** – Dörren till backend
- **Auth-service** – användare och jwt hantering.
- **Product-service** – produkter och lagersaldo.
- **Order-service** –  sköter ordrar. Minskar lagersaldot och skickar orderbekräftelse via RabbitMQ.
- **Email-service** – skickar orderbekräftelser.

## Webshoppens repositorys
- Auth-service: https://github.com/katttiger/auth-service
- Product-service: https://github.com/Joajak92/product-service
- Email-service: https://github.com/joel-schou/email-service 
- Gateway-service: https://github.com/joel-schou/gateway-service 
- Webshop-frontend: https://github.com/Joajak92/webshop-frontend

## Starta lokalt
1. Sätt miljövariablerna.
2. Starta auth-service och product-service.
3. Starta applikationen på Mac: ./mvnw spring-boot:run
         Windows: mvnw.cmd spring-boot:run
4. Nås på http://localhost:7500
   




