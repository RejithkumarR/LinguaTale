package com.linguatale.api.job;

import com.linguatale.api.story.StoryService;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class JobService {
    private final JobRepository repository;
    private final StoryService stories;
    private final JobPublisher publisher;
    public JobService(JobRepository repository, StoryService stories, JobPublisher publisher) { this.repository = repository; this.stories = stories; this.publisher = publisher; }
    public GenerationJob create(long storyId, String language, String voice) {
        stories.get(storyId);
        String id = UUID.randomUUID().toString();
        repository.create(id, storyId, language, voice);
        publisher.publish(id);
        return repository.get(id).orElseThrow();
    }
    public GenerationJob get(String id) { return repository.get(id).orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "Job not found")); }
    public void updateStatus(String id, String state, int progress, String error) { repository.status(id, state, progress, error); }
    public void saveAudio(String id, long storyId, String language, String key, String contentType) { repository.audio(id, storyId, language, key, contentType); }
}