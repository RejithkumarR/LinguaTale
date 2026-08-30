# LinguaTale

> **Write it once. Hear it everywhere.**

## 1. Project Overview

**LinguaTale** is an AI-powered multilingual storytelling platform that transforms written stories into natural, expressive audio experiences across multiple languages.

The core concept is simple:

```text
Write a Story
     ↓
Understand the Story
     ↓
Translate into Selected Languages
     ↓
Generate Natural Narration
     ↓
Process & Store Audio
     ↓
Listen / Download / Share
```

LinguaTale should be designed as a platform rather than a simple text-to-speech application. The architecture must support future capabilities such as character voices, scene-based narration, background music, sound effects, subtitles, illustrations, audiobooks, and AI-assisted story creation.

---

# 2. Product Vision

A user creates one story and LinguaTale turns it into multiple localized storytelling experiences.

```text
                         ┌─────────────────┐
                         │   ONE STORY     │
                         └────────┬────────┘
                                  │
                 ┌────────────────┼────────────────┐
                 ↓                ↓                ↓
            Translation      Story Analysis      Scenes
                 │                │                │
                 └────────────────┼────────────────┘
                                  ↓
                         Narration Planning
                                  │
                 ┌────────────────┼────────────────┐
                 ↓                ↓                ↓
              English           Tamil            Hindi
                 ↓                ↓                ↓
               Audio            Audio            Audio
```

### Long-term vision

LinguaTale can evolve into an AI storytelling ecosystem supporting:

- Multilingual story translation
- Natural AI narration
- Character-specific voices
- Emotion-aware narration
- Scene detection
- Chapters
- Background music
- Sound effects
- Automatic subtitles
- Story illustrations
- AI story generation
- AI story continuation
- Audiobook creation
- Educational content
- Children's stories
- Podcast-style storytelling

---

# 3. Architecture Principles

LinguaTale should follow these principles:

1. Mobile-first
2. API-first
3. AI-provider independent
4. Translation-provider independent
5. TTS-provider independent
6. Asynchronous processing
7. Real-time job progress
8. Secure API-key management
9. Cloud-ready
10. Scalable workers
11. Observable processing
12. Incremental feature development

The most important separation is:

> **Flutter knows what the user wants, the backend manages the application, and Python AI knows how to generate it.**

---

# 4. High-Level Architecture

```text
┌──────────────────────────────────────────────────────────────┐
│                       LINGUATALE                             │
│                                                              │
│                    Flutter Mobile App                       │
│                                                              │
│  Auth │ Stories │ Editor │ Languages │ Audio │ Downloads    │
└──────────────────────────────┬───────────────────────────────┘
                               │ HTTPS
                               ▼
┌──────────────────────────────────────────────────────────────┐
│                         API LAYER                             │
│                                                              │
│                 Backend API / BFF                            │
│                                                              │
│ Auth │ Authorization │ Stories │ Jobs │ Users │ Metadata    │
└──────────────────────┬───────────────────────────────────────┘
                       │
                       ▼
              ┌────────────────────┐
              │    MESSAGE QUEUE   │
              │                    │
              │ RabbitMQ / Kafka   │
              │ Cloud Queue        │
              └─────────┬──────────┘
                        │
                        ▼
┌──────────────────────────────────────────────────────────────┐
│                       PYTHON AI LAYER                         │
│                                                              │
│ AI Orchestrator                                               │
│                                                              │
│ Story Analysis │ Translation │ Narration │ TTS │ Audio      │
└──────────────┬───────────────────────┬────────────────────────┘
               │                       │
               ▼                       ▼
       ┌───────────────┐       ┌────────────────┐
       │ AI Providers  │       │ Audio Pipeline │
       │               │       │                │
       │ LLM           │       │ Normalize      │
       │ Translation   │       │ Merge          │
       │ TTS           │       │ Music / FX     │
       └───────────────┘       └───────┬────────┘
                                       │
                    ┌──────────────────┼─────────────────┐
                    ▼                  ▼                 ▼
              ┌──────────┐       ┌──────────┐     ┌─────────────┐
              │ Database │       │  Redis   │     │Object Store │
              │          │       │          │     │             │
              │ SQL/NoSQL│       │ Cache    │     │ Audio/Media │
              └──────────┘       └──────────┘     └─────────────┘
```

---

# 5. Technology Stack

## Mobile

- Flutter
- Dart
- Clean Architecture
- Feature-based structure
- Riverpod / Bloc
- Secure local storage
- Offline audio support

## Backend

Choose **one** primary business backend:

### Option A — Java

- Java
- Spring Boot
- Spring Security
- REST APIs
- JPA / Hibernate

### Option B — C#

- C#
- ASP.NET Core
- ASP.NET Core Identity / external identity provider
- REST APIs
- Entity Framework Core

> Do not maintain both Java and C# initially unless there is a real architectural requirement. Choose one as the primary backend.

## AI Layer

- Python
- FastAPI
- Async workers
- AI orchestration
- Translation integration
- TTS integration
- Audio processing

## Infrastructure

- Docker
- Object storage
- Redis
- Message queue
- SQL or MongoDB
- CI/CD
- Cloud deployment

---

# 6. Flutter Architecture

LinguaTale mobile should use **Clean Architecture + Feature-Based Architecture**.

```text
mobile/
└── flutter_app/
    ├── lib/
    │   ├── core/
    │   │   ├── constants/
    │   │   ├── errors/
    │   │   ├── network/
    │   │   ├── storage/
    │   │   ├── audio/
    │   │   ├── theme/
    │   │   └── utilities/
    │   │
    │   ├── features/
    │   │   ├── authentication/
    │   │   ├── stories/
    │   │   ├── story_editor/
    │   │   ├── translation/
    │   │   ├── narration/
    │   │   ├── audio_player/
    │   │   ├── downloads/
    │   │   └── settings/
    │   │
    │   └── main.dart
    │
    └── test/
```

Each feature should follow:

```text
feature/
├── data/
│   ├── datasources/
│   ├── models/
│   └── repositories/
│
├── domain/
│   ├── entities/
│   ├── repositories/
│   └── usecases/
│
└── presentation/
    ├── pages/
    ├── widgets/
    └── controllers/
```

---

# 7. Backend Architecture

The backend owns application/business responsibilities.

```text
backend/
└── api/
    ├── authentication/
    ├── users/
    ├── stories/
    ├── scenes/
    ├── languages/
    ├── voices/
    ├── jobs/
    ├── subscriptions/
    ├── usage/
    ├── notifications/
    └── common/
```

### Backend responsibilities

- Authentication
- User management
- Authorization
- Story CRUD
- Story metadata
- Language management
- Voice catalog
- Job creation
- Job tracking
- Usage tracking
- Subscription management
- Notifications
- Audit logging
- API security

The backend should not contain provider-specific AI prompts or complicated AI orchestration.

---

# 8. Python AI Architecture

Python is the AI orchestration layer.

```text
ai/
└── linguatale-ai/
    ├── api/
    │
    ├── application/
    │   ├── story_service.py
    │   ├── translation_service.py
    │   ├── narration_service.py
    │   └── audio_service.py
    │
    ├── domain/
    │   ├── story.py
    │   ├── scene.py
    │   ├── character.py
    │   └── narration.py
    │
    ├── ai/
    │   ├── llm/
    │   ├── translation/
    │   ├── tts/
    │   ├── prompts/
    │   └── orchestration/
    │
    ├── workers/
    │   ├── story_worker.py
    │   ├── translation_worker.py
    │   ├── tts_worker.py
    │   └── audio_worker.py
    │
    ├── infrastructure/
    │   ├── database/
    │   ├── storage/
    │   ├── queue/
    │   └── cache/
    │
    └── main.py
```

---

# 9. AI Processing Pipeline

```text
                         RAW STORY
                             │
                             ▼
                     ┌───────────────┐
                     │ Story Analyzer │
                     └───────┬───────┘
                             │
              ┌──────────────┼──────────────┐
              ▼              ▼              ▼
       Language Detect   Characters       Scenes
              │              │              │
              └──────────────┼──────────────┘
                             ▼
                     Normalized Story
                             │
                             ▼
                    Translation Engine
                             │
                             ▼
                     Localized Story
                             │
                             ▼
                    Narration Planner
                             │
              ┌──────────────┼──────────────┐
              ▼              ▼              ▼
           Voice          Emotion          Speed
              │              │              │
              └──────────────┼──────────────┘
                             ▼
                        TTS Engine
                             │
                             ▼
                         Raw Audio
                             │
                             ▼
                      Audio Processor
                             │
              ┌──────────────┼──────────────┐
              ▼              ▼              ▼
           Normalize        Merge       Music / FX
              │              │              │
              └──────────────┼──────────────┘
                             ▼
                       Final Audio
```

---

# 10. AI Provider Abstraction

Never tightly couple LinguaTale to a single AI provider.

Use adapter interfaces.

```text
                    AI Provider Layer
                           │
          ┌────────────────┼────────────────┐
          ▼                ▼                ▼
     LLM Provider    Translation Provider   TTS Provider
          │                │                │
     ┌────┼────┐      ┌────┼────┐      ┌────┼────┐
     ▼    ▼    ▼      ▼    ▼    ▼      ▼    ▼    ▼
   A      B   Local    A    B   Local    A    B   Local
```

Example abstraction:

```python
class TranslationProvider:
    async def translate(
        self,
        text: str,
        source_language: str,
        target_language: str
    ) -> str:
        ...
```

The application uses the abstraction rather than a provider directly.

This allows providers to be changed later without rewriting the application.

---

# 11. Story Processing Model

A story should not be treated as a single large text blob.

Recommended hierarchy:

```text
Story
 │
 ├── Metadata
 │
 ├── Chapters
 │    │
 │    ├── Scenes
 │    │    │
 │    │    ├── Narration
 │    │    ├── Dialogue
 │    │    └── Characters
 │    │
 │    └── Audio
 │
 └── Translations
      ├── English
      ├── Tamil
      ├── Hindi
      └── Other languages
```

This allows individual scenes or languages to be regenerated without processing the entire story again.

---

# 12. Example Story Model

```json
{
  "storyId": "story-001",
  "title": "The Lost Kingdom",
  "sourceLanguage": "en",
  "content": "...",
  "chapters": [
    {
      "chapterId": "chapter-001",
      "title": "The Journey",
      "scenes": [
        {
          "sceneId": "scene-001",
          "order": 1,
          "content": "...",
          "characters": [
            "narrator",
            "hero"
          ]
        }
      ]
    }
  ],
  "translations": [
    {
      "language": "ta",
      "status": "completed"
    },
    {
      "language": "hi",
      "status": "processing"
    }
  ],
  "audioVersions": [
    {
      "language": "en",
      "status": "completed",
      "duration": 420
    }
  ]
}
```

---

# 13. Asynchronous Job Architecture

Translation and audio generation can take significant time.

The mobile application should never keep a normal HTTP request open waiting for the complete generation.

Use background jobs.

```text
Flutter
   │
   │ POST /stories/{id}/generate
   ▼
Backend
   │
   ├── Create Job
   └── Publish Job
          │
          ▼
     Message Queue
          │
          ▼
     Python Worker
          │
          ├── Analyze
          ├── Translate
          ├── Generate Voice
          ├── Process Audio
          └── Store Result
          │
          ▼
      Job Completed
```

---

# 14. Job State Machine

```text
CREATED
   │
   ▼
QUEUED
   │
   ▼
PROCESSING
   │
   ├── ANALYZING
   │
   ├── TRANSLATING
   │
   ├── GENERATING_AUDIO
   │
   └── PROCESSING_AUDIO
          │
          ▼
      COMPLETED
```

Failure path:

```text
PROCESSING
    │
    ▼
   ERROR
    │
    ▼
  RETRY
    │
    ▼
PROCESSING
```

Jobs should have:

- Job ID
- Story ID
- User ID
- Job type
- Status
- Progress
- Retry count
- Error information
- Created time
- Started time
- Completed time

---

# 15. Real-Time Progress

The user should never need to refresh the application to see updated processing status.

Use:

- WebSocket
- Server-Sent Events
- Push notifications

Example:

```text
Story generation

10%  Story analyzed
30%  Translation completed
60%  Voice generated
85%  Audio processing
100% Completed
```

The same real-time architecture should later be used for:

- Story updates
- Translation completion
- Audio completion
- Download availability
- Notifications

---

# 16. Language Architecture

Languages must be data-driven rather than hard-coded.

```text
Language
├── code
├── name
├── nativeName
├── translationSupported
├── ttsSupported
├── availableVoices
└── enabled
```

Potential languages:

- English
- Tamil
- Hindi
- Telugu
- Malayalam
- Kannada
- Bengali
- Marathi
- Spanish
- French
- German
- Japanese
- Arabic

New languages should be addable through configuration/database changes wherever possible.

---

# 17. Voice Architecture

Voice selection should also be provider-independent.

```text
Voice
├── voiceId
├── provider
├── language
├── gender
├── style
├── supportedEmotions
└── enabled
```

Example narration request:

```json
{
  "language": "ta",
  "voice": "female-storyteller",
  "style": "storytelling",
  "emotion": "warm",
  "speed": 0.95
}
```

The Python AI layer decides which provider and actual voice configuration should be used.

---

# 18. Audio Architecture

Audio should be processed after TTS generation.

```text
TTS
 │
 ▼
Raw Audio
 │
 ├── Normalize volume
 ├── Remove unwanted silence
 ├── Merge scene audio
 ├── Add optional background music
 ├── Add optional sound effects
 └── Encode final format
 │
 ▼
Final Audio
```

Possible formats:

- MP3
- AAC
- WAV for intermediate processing

The application should prefer compressed formats for downloads and streaming.

---

# 19. Storage Architecture

Use the correct storage system for each type of data.

```text
Database
│
├── Users
├── Stories
├── Chapters
├── Scenes
├── Languages
├── Voices
├── Jobs
└── Metadata

Redis
│
├── Cache
├── Temporary state
└── Job status

Object Storage
│
├── Original stories
├── Translations
├── Audio
├── Cover images
└── Other generated media
```

Large audio files should not be stored directly inside the main database.

---

# 20. Object Storage Structure

```text
stories/
└── {storyId}/
    │
    ├── original/
    │   └── story.txt
    │
    ├── translations/
    │   ├── en/
    │   ├── ta/
    │   ├── hi/
    │   └── te/
    │
    └── audio/
        ├── en/
        │   ├── chapter-01.mp3
        │   └── chapter-02.mp3
        │
        ├── ta/
        │   ├── chapter-01.mp3
        │   └── chapter-02.mp3
        │
        └── hi/
            ├── chapter-01.mp3
            └── chapter-02.mp3
```

---

# 21. API Design

Recommended API structure:

```text
/api/v1/auth
/api/v1/users
/api/v1/stories
/api/v1/stories/{id}
/api/v1/stories/{id}/chapters
/api/v1/stories/{id}/scenes
/api/v1/stories/{id}/translations
/api/v1/stories/{id}/audio
/api/v1/stories/{id}/generate
/api/v1/jobs
/api/v1/jobs/{id}
/api/v1/languages
/api/v1/voices
```

Example:

```http
POST /api/v1/stories
```

Create a story.

```http
GET /api/v1/stories/{id}
```

Retrieve a story.

```http
POST /api/v1/stories/{id}/translations
```

Request translation.

```http
POST /api/v1/stories/{id}/audio
```

Request audio generation.

```http
GET /api/v1/jobs/{jobId}
```

Retrieve processing status.

---

# 22. Security

AI provider credentials must never be included in the Flutter application.

```text
Flutter
   │
   ▼
API Gateway
   │
   ├── Authentication
   ├── Authorization
   ├── Rate Limiting
   ├── Request Validation
   └── Audit Logging
           │
           ▼
      Backend / AI
```

Recommended security controls:

- OAuth2 / OpenID Connect
- JWT access tokens
- Refresh tokens
- Role-based authorization
- API rate limiting
- Request validation
- Secure object-storage URLs
- Encryption in transit
- Encryption at rest
- Secret management
- AI usage limits
- Audit logging

---

# 23. Observability

Every AI operation should be traceable.

```text
Request ID
    │
    ├── User
    ├── Story
    ├── Job
    ├── Provider
    ├── Model
    ├── Language
    ├── Processing time
    ├── Token usage
    ├── Audio duration
    └── Estimated cost
```

Monitor:

- API latency
- AI latency
- Translation failures
- TTS failures
- Queue depth
- Worker health
- Storage usage
- AI cost
- Audio generation time
- Error rates

---

# 24. CI/CD

```text
Git Repository
      │
      ├──────────────┬──────────────┬──────────────┐
      ▼              ▼              ▼              ▼
   Flutter        Backend        Python AI    Infrastructure
      │              │              │              │
      ▼              ▼              ▼              ▼
   Analyze         Test           Test           Validate
   Test            Build          Lint           Security
   Build
      │              │              │              │
      └──────────────┴──────────────┴──────────────┘
                             │
                             ▼
                       Docker Images
                             │
                             ▼
                        Container
                         Registry
                             │
                             ▼
                         Deployment
```

CI should run only when relevant source/configuration changes occur when practical, while shared infrastructure changes can trigger the appropriate services.

---

# 25. Repository Structure

Recommended monorepo:

```text
linguatale/
│
├── mobile/
│   └── flutter_app/
│
├── backend/
│   └── api/
│
├── ai/
│   └── linguatale-ai/
│
├── infrastructure/
│   ├── docker/
│   ├── kubernetes/
│   └── terraform/
│
├── docs/
│   ├── architecture/
│   ├── api/
│   ├── ai/
│   └── product/
│
├── scripts/
│
├── .github/
│   └── workflows/
│
└── README.md
```

---

# 26. MVP

The first version should remain focused.

## Phase 1 — MVP

```text
Flutter
   ↓
Backend
   ↓
Python AI
   ↓
Translation
   ↓
TTS
   ↓
Audio
```

### MVP features

- User authentication
- Create story
- Edit story
- Save story
- Story library
- Select target language
- Translate story
- Select voice
- Generate audio
- Audio player
- Download audio
- Processing progress
- Error/retry handling

---

# 27. Phase 2

Add:

- Chapters
- Scene detection
- Multiple voices
- Character detection
- Character-specific voices
- Emotion-aware narration
- Background music
- Sound effects
- Better audio controls
- Automatic subtitles

---

# 28. Phase 3

Add:

- AI story generation
- AI story continuation
- Story illustrations
- Audiobook generation
- Advanced voice customization
- Story sharing
- Public story library
- Creator profiles
- Social features

---

# 29. Future AI Story Engine

The long-term architecture can evolve into:

```text
                         LINGUATALE
                              │
             ┌────────────────┼────────────────┐
             │                │                │
          STORIES         CHARACTERS         MEDIA
             │                │                │
             ▼                ▼                ▼
        Story Engine     Character Engine   Media Engine
             │                │                │
             └────────────────┼────────────────┘
                              │
                         AI ORCHESTRATOR
                              │
       ┌──────────────┬───────┼────────┬──────────────┐
       ▼              ▼       ▼        ▼              ▼
      LLM       Translation   TTS     Music        Sound FX
       │              │       │        │              │
       └──────────────┴───────┼────────┴──────────────┘
                              ▼
                       AUDIO PIPELINE
                              │
                              ▼
                    MULTILINGUAL STORY
                              │
                 ┌────────────┼────────────┐
                 ▼            ▼            ▼
              English       Tamil        Hindi
                 │            │            │
                 ▼            ▼            ▼
               Audio        Audio        Audio
```

---

# 30. Naming

## Product

**LinguaTale**

## Meaning

**Lingua** → Language  
**Tale** → Story

Together:

> **LinguaTale = Stories without language boundaries.**

## Taglines

Primary:

> **Write it once. Hear it everywhere.**

Alternative:

> **Every story deserves every language.**

Alternative:

> **Your story. Every voice. Every language.**

---

# 31. Development Strategy

Build the system incrementally.

### Step 1

Create the Flutter application shell.

### Step 2

Create the backend API.

### Step 3

Implement authentication.

### Step 4

Implement story creation and persistence.

### Step 5

Create Python AI service.

### Step 6

Implement translation provider abstraction.

### Step 7

Implement TTS provider abstraction.

### Step 8

Implement asynchronous job processing.

### Step 9

Implement audio storage.

### Step 10

Implement Flutter audio player.

### Step 11

Implement real-time processing updates.

### Step 12

Add advanced storytelling capabilities.

---

# 32. Critical Architecture Rule

Do not build LinguaTale like this:

```text
Flutter
   │
   ├── Direct LLM API
   ├── Direct Translation API
   └── Direct TTS API
```

Instead:

```text
Flutter
   │
   ▼
Backend API
   │
   ▼
Job Queue
   │
   ▼
Python AI Orchestrator
   │
   ├── LLM Adapter
   ├── Translation Adapter
   ├── TTS Adapter
   └── Audio Processor
```

This protects credentials, centralizes business logic, allows provider changes, supports retries, enables scaling, and keeps the mobile application clean.

---

# 33. Final Architecture Goal

LinguaTale should start as:

> **A mobile application that converts written stories into multilingual narrated audio.**

But the architecture should allow it to become:

> **A complete AI storytelling platform where a single written story can become localized text, expressive narration, characters, scenes, music, sound effects, subtitles, illustrations, and eventually a complete audiobook.**

The architecture should therefore optimize for **extensibility without over-engineering the MVP**.
