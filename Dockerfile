FROM eclipse-temurin:17-jre
WORKDIR /app
COPY target/projekt-order-service-1.0.1.jar.jar app.jar
EXPOSE 7500
ENTRYPOINT ["java", "-jar", "app.jar"]