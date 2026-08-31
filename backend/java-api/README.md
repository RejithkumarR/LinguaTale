# LinguaTale Java API

Spring Boot business/API layer for LinguaTale.

## Responsibilities

- User and story APIs
- Authentication and authorization boundary
- Job creation and status
- Usage and metadata
- Integration boundary to the Python AI service

## Run

```bash
mvn spring-boot:run
```

Health endpoint:

```text
GET http://localhost:8080/api/v1/health
```
