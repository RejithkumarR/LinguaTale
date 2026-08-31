USE linguatale;
DROP PROCEDURE IF EXISTS sp_story_create;
DROP PROCEDURE IF EXISTS sp_story_get;
DROP PROCEDURE IF EXISTS sp_story_list;
DROP PROCEDURE IF EXISTS sp_story_update;
DROP PROCEDURE IF EXISTS sp_story_delete;
DROP PROCEDURE IF EXISTS sp_job_create;
DROP PROCEDURE IF EXISTS sp_job_get;
DROP PROCEDURE IF EXISTS sp_job_update_status;
DROP PROCEDURE IF EXISTS sp_translation_upsert;
DROP PROCEDURE IF EXISTS sp_audio_create;

DELIMITER $$
CREATE PROCEDURE sp_story_create(IN p_title VARCHAR(200), IN p_content LONGTEXT, IN p_source_language VARCHAR(10), OUT p_story_id BIGINT)
BEGIN INSERT INTO stories(title, content, source_language) VALUES(p_title,p_content,p_source_language); SET p_story_id=LAST_INSERT_ID(); END$$
CREATE PROCEDURE sp_story_get(IN p_story_id BIGINT)
BEGIN SELECT id,title,content,source_language,created_at,updated_at FROM stories WHERE id=p_story_id; END$$
CREATE PROCEDURE sp_story_list()
BEGIN SELECT id,title,content,source_language,created_at,updated_at FROM stories ORDER BY updated_at DESC,id DESC; END$$
CREATE PROCEDURE sp_story_update(IN p_story_id BIGINT, IN p_title VARCHAR(200), IN p_content LONGTEXT, IN p_source_language VARCHAR(10), OUT p_affected INT)
BEGIN UPDATE stories SET title=p_title,content=p_content,source_language=p_source_language WHERE id=p_story_id; SET p_affected=ROW_COUNT(); END$$
CREATE PROCEDURE sp_story_delete(IN p_story_id BIGINT, OUT p_affected INT)
BEGIN DELETE FROM stories WHERE id=p_story_id; SET p_affected=ROW_COUNT(); END$$
CREATE PROCEDURE sp_job_create(IN p_job_id VARCHAR(36), IN p_story_id BIGINT, IN p_target_language VARCHAR(10), IN p_voice VARCHAR(100))
BEGIN INSERT INTO generation_jobs(id,story_id,target_language,voice,status,progress) VALUES(p_job_id,p_story_id,p_target_language,p_voice,'QUEUED',0); END$$
CREATE PROCEDURE sp_job_get(IN p_job_id VARCHAR(36))
BEGIN SELECT id,story_id,target_language,voice,status,progress,audio_key,error_message,created_at FROM generation_jobs WHERE id=p_job_id; END$$
CREATE PROCEDURE sp_job_update_status(IN p_job_id VARCHAR(36), IN p_status VARCHAR(30), IN p_progress INT, IN p_error_message TEXT)
BEGIN UPDATE generation_jobs SET status=p_status,progress=p_progress,error_message=NULLIF(p_error_message,''),updated_at=CURRENT_TIMESTAMP(6) WHERE id=p_job_id; END$$
CREATE PROCEDURE sp_translation_upsert(IN p_story_id BIGINT, IN p_language VARCHAR(10), IN p_content LONGTEXT)
BEGIN INSERT INTO translations(story_id,language,content) VALUES(p_story_id,p_language,p_content) ON DUPLICATE KEY UPDATE content=VALUES(content),updated_at=CURRENT_TIMESTAMP(6); END$$
CREATE PROCEDURE sp_audio_create(IN p_job_id VARCHAR(36), IN p_story_id BIGINT, IN p_language VARCHAR(10), IN p_storage_key VARCHAR(500), IN p_content_type VARCHAR(100))
BEGIN INSERT INTO audio_assets(job_id,story_id,language,storage_key,content_type) VALUES(p_job_id,p_story_id,p_language,p_storage_key,p_content_type); UPDATE generation_jobs SET audio_key=p_storage_key WHERE id=p_job_id; END$$
DELIMITER ;