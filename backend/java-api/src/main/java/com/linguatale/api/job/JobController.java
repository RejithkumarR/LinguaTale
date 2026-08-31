package com.linguatale.api.job;

import java.time.Instant;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/jobs")
public class JobController {
    @PostMapping
    public ResponseEntity<GenerationJob> createJob() {
        var job = new GenerationJob(
                UUID.randomUUID().toString(),
                "pending-story",
                "QUEUED",
                0,
                Instant.now());
        return ResponseEntity.accepted().body(job);
    }

    @GetMapping("/{jobId}")
    public ResponseEntity<GenerationJob> getJob(@PathVariable String jobId) {
        return ResponseEntity.ok(new GenerationJob(jobId, "pending-story", "QUEUED", 0, Instant.now()));
    }
}
