from pydantic_settings import BaseSettings, SettingsConfigDict

class Settings(BaseSettings):
    openai_api_key: str
    openai_model: str = "gpt-5.6-luna"
    openai_tts_model: str = "gpt-4o-mini-tts"
    java_api_url: str = "http://localhost:8080"
    ai_callback_secret: str = "change-me"
    rabbitmq_url: str = "amqp://guest:guest@localhost:5672/"
    generation_queue: str = "linguatale.generation"
    s3_endpoint_url: str = "http://localhost:9000"
    s3_access_key: str = "minio"
    s3_secret_key: str = "minio123"
    s3_bucket: str = "linguatale-audio"
    s3_region: str = "us-east-1"
    model_config = SettingsConfigDict(env_file=".env", extra="ignore")

settings = Settings()