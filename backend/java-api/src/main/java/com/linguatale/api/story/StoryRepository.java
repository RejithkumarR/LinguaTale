package com.linguatale.api.story;

import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class StoryRepository {
    private final SimpleJdbcCall create;
    private final SimpleJdbcCall get;
    private final SimpleJdbcCall list;
    private final SimpleJdbcCall update;
    private final SimpleJdbcCall delete;

    public StoryRepository(DataSource dataSource) {
        this.create = new SimpleJdbcCall(dataSource).withProcedureName("sp_story_create");
        this.get = new SimpleJdbcCall(dataSource).withProcedureName("sp_story_get");
        this.list = new SimpleJdbcCall(dataSource).withProcedureName("sp_story_list");
        this.update = new SimpleJdbcCall(dataSource).withProcedureName("sp_story_update");
        this.delete = new SimpleJdbcCall(dataSource).withProcedureName("sp_story_delete");
    }

    public Long create(StoryRequest request) {
        Map<String, Object> out = create.execute(Map.of("p_title", request.title(), "p_content", request.content(), "p_source_language", request.sourceLanguage()));
        return ((Number) out.get("p_story_id")).longValue();
    }

    public Optional<Story> findById(long id) {
        Map<String, Object> out = get.execute(Map.of("p_story_id", id));
        return rows(out).stream().findFirst();
    }

    public List<Story> findAll() {
        return rows(list.execute());
    }

    public boolean update(long id, StoryRequest request) {
        Map<String, Object> out = update.execute(Map.of("p_story_id", id, "p_title", request.title(), "p_content", request.content(), "p_source_language", request.sourceLanguage()));
        return ((Number) out.getOrDefault("p_affected", 0)).intValue() > 0;
    }

    public boolean delete(long id) {
        Map<String, Object> out = delete.execute(Map.of("p_story_id", id));
        return ((Number) out.getOrDefault("p_affected", 0)).intValue() > 0;
    }

    @SuppressWarnings("unchecked")
    private List<Story> rows(Map<String, Object> out) {
        Object value = out.get("#result-set-1");
        if (!(value instanceof List<?> raw)) return List.of();
        return ((List<Map<String, Object>>) raw).stream().map(this::map).toList();
    }

    private Story map(Map<String, Object> row) {
        Timestamp created = (Timestamp) row.get("created_at");
        Timestamp updated = (Timestamp) row.get("updated_at");
        return new Story(((Number) row.get("id")).longValue(), (String) row.get("title"), (String) row.get("content"), (String) row.get("source_language"), created.toInstant(), updated.toInstant());
    }
}