package com.linguatale.api.job;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/internal/jobs")
public class AiCallbackController {
    private final JobService service;
    private final String secret;
    public AiCallbackController(JobService service, @Value("${linguatale.ai.callback-secret}") String secret) { this.service = service; this.secret = secret; }
    public record Callback(String status, int progress, String error, long storyId, String language, String audioKey, String contentType) {}
    @PostMapping("/{id}/callback") public void callback(@PathVariable String id, @RequestHeader("X-LinguaTale-Secret") String provided, @RequestBody Callback request) {
        if (!secret.equals(provided)) throw new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.UNAUTHORIZED);
        service.updateStatus(id, request.status(), request.progress(), request.error());
        if (request.audioKey() != null) service.saveAudio(id, request.storyId(), request.language(), request.audioKey(), request.contentType());
    }
}