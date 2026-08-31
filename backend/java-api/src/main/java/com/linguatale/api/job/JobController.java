package com.linguatale.api.job;

import jakarta.validation.constraints.NotBlank;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/jobs")
public class JobController {
    private final JobService service;
    public JobController(JobService service) { this.service = service; }
    public record CreateJobRequest(long storyId, @NotBlank String targetLanguage, String voice) {}
    @PostMapping("/generation") public GenerationJob create(@RequestBody CreateJobRequest request) { return service.create(request.storyId(), request.targetLanguage(), request.voice() == null ? "alloy" : request.voice()); }
    @GetMapping("/{id}") public GenerationJob get(@PathVariable String id) { return service.get(id); }
}