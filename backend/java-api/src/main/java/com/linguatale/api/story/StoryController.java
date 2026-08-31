package com.linguatale.api.story;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/stories")
public class StoryController {
    private final StoryService service;
    public StoryController(StoryService service) { this.service = service; }
    @PostMapping public Story create(@Valid @RequestBody StoryRequest request) { return service.create(request); }
    @GetMapping public List<Story> list() { return service.list(); }
    @GetMapping("/{id}") public Story get(@PathVariable long id) { return service.get(id); }
    @PutMapping("/{id}") public Story update(@PathVariable long id, @Valid @RequestBody StoryRequest request) { return service.update(id, request); }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void delete(@PathVariable long id) { service.delete(id); }
}