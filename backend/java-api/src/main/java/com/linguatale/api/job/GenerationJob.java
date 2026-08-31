package com.linguatale.api.job;

import java.time.Instant;

public record GenerationJob(String id, long storyId, String targetLanguage, String voice, String status, int progress, String audioKey, String error, Instant createdAt) {}