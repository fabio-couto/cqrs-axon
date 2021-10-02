# Event-Driven Microservices, CQRS, SAGA, Axon, Spring Boot

### Descrição
Material desenvolvido durante o curso [Event-Driven Microservices, CQRS, SAGA, Axon, Spring Boot](https://www.udemy.com/course/spring-boot-microservices-cqrs-saga-axon-framework/)

### Para iniciar os serviços do Spring Cloud
```
gradlew discovery-server:bootRun
gradlew api-gateway:bootRun
```

### Para iniciar os microserviços
```
gradlew orders-service:bootRun
gradlew payment-service:bootRun
gradlew product-service:bootRun
gradlew users-service:bootRun
```

### Tecnologias
Java 11, Gradle, Spring Boot, Spring Cloud, Axon