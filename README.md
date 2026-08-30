# LinguaTale

> Write it once. Hear it everywhere.

LinguaTale is an AI-powered multilingual storytelling platform that transforms written stories into expressive audio experiences across multiple languages.

## Architecture

The initial architecture is documented in [`docs/architecture/LinguaTale_Architecture.md`](docs/architecture/LinguaTale_Architecture.md).

## Planned platform

- Flutter mobile application
- Java or C# business backend
- Python AI orchestration layer
- Translation and text-to-speech provider adapters
- Asynchronous processing with a message queue
- Database for application metadata
- Object storage for generated audio and media
- Real-time processing progress
- CI/CD and cloud-ready infrastructure

## Initial scope

1. Create and edit stories
2. Translate stories into selected languages
3. Generate narrated audio
4. Play and download generated audio
5. Track asynchronous generation jobs

The repository is intentionally being established in small, reviewable increments so the MVP remains maintainable while preserving room for future character voices, scenes, music, sound effects, subtitles, illustrations, and audiobook generation.
