# LinguaTale

> Write it once. Hear it everywhere.

LinguaTale is an AI-powered multilingual storytelling platform.

## Current architecture

- Flutter mobile application
- Java 21 Spring Boot API
- MySQL persistence through stored procedures only
- RabbitMQ asynchronous generation jobs
- Python FastAPI AI orchestration/worker
- OpenAI-backed story analysis, translation and TTS adapters
- S3-compatible object storage for generated audio

## Local infrastructure

```bash
docker compose up -d
```

Set `OPENAI_API_KEY` and run the Java and Python services. Database procedures are under `database/mysql/` and are loaded automatically by the MySQL container on first initialization.

## Database rule

Java application code must not contain inline SQL for application CRUD. Database access is performed through named MySQL stored procedures. Schema/procedure definitions are versioned under `database/mysql/`.

## Product

Create one story, translate it into multiple languages, generate expressive narration, and store the resulting audio for playback and download.