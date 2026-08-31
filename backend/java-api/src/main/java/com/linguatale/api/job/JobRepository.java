package com.linguatale.api.job;

import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.sql.Timestamp;
import java.util.Map;
import java.util.Optional;

@Repository
public class JobRepository {
    private final SimpleJdbcCall create;
    private final SimpleJdbcCall get;
    private final SimpleJdbcCall status;
    private final SimpleJdbcCall audio;
    public JobRepository(DataSource ds) {
        create = new SimpleJdbcCall(ds).withProcedureName("sp_job_create");
        get = new SimpleJdbcCall(ds).withProcedureName("sp_job_get");
        status = new SimpleJdbcCall(ds).withProcedureName("sp_job_update_status");
        audio = new SimpleJdbcCall(ds).withProcedureName("sp_audio_create");
    }
    public void create(String id, long storyId, String language, String voice) { create.execute(Map.of("p_job_id", id, "p_story_id", storyId, "p_target_language", language, "p_voice", voice)); }
    public Optional<GenerationJob> get(String id) {
        Map<String,Object> out = get.execute(Map.of("p_job_id", id));
        Object value = out.get("#result-set-1");
        if (!(value instanceof java.util.List<?> rows) || rows.isEmpty()) return Optional.empty();
        @SuppressWarnings("unchecked") Map<String,Object> row = (Map<String,Object>) rows.getFirst();
        Timestamp created = (Timestamp) row.get("created_at");
        return Optional.of(new GenerationJob((String)row.get("id"), ((Number)row.get("story_id")).longValue(), (String)row.get("target_language"), (String)row.get("voice"), (String)row.get("status"), ((Number)row.get("progress")).intValue(), (String)row.get("audio_key"), (String)row.get("error_message"), created.toInstant()));
    }
    public void status(String id, String state, int progress, String error) { status.execute(Map.of("p_job_id", id, "p_status", state, "p_progress", progress, "p_error_message", error == null ? "" : error)); }
    public void audio(String id, long storyId, String language, String key, String contentType) { audio.execute(Map.of("p_job_id", id, "p_story_id", storyId, "p_language", language, "p_storage_key", key, "p_content_type", contentType)); }
}