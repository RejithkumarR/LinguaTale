package com.linguatale.api.story;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
public class StoryService {
    private final StoryRepository repository;
    public StoryService(StoryRepository repository) { this.repository = repository; }
    public Story create(StoryRequest request) { return repository.findById(repository.create(request)).orElseThrow(); }
    public Story get(long id) { return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Story not found")); }
    public List<Story> list() { return repository.findAll(); }
    public Story update(long id, StoryRequest request) { if (!repository.update(id, request)) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Story not found"); return get(id); }
    public void delete(long id) { if (!repository.delete(id)) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Story not found"); }
}