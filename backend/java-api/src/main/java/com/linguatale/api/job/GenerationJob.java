package com.linguatale.api.job;

import java.time.Instant;

public record GenerationJob(
        String jobId,
        String storyId,
        String status,
        int progress,
        Instant createdAt) {
}
