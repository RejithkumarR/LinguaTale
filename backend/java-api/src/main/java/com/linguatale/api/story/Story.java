package com.linguatale.api.story;

import java.time.Instant;

public record Story(Long id, String title, String content, String sourceLanguage, Instant createdAt, Instant updatedAt) {}