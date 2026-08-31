from contextlib import asynccontextmanager
from fastapi import FastAPI
from pydantic import BaseModel
from app.providers.openai_provider import OpenAIProvider
from app.worker import start_worker

worker_task = None
provider = OpenAIProvider()

@asynccontextmanager
async def lifespan(app: FastAPI):
    global worker_task
    worker_task = await start_worker()
    yield
    if worker_task:
        worker_task.cancel()

app = FastAPI(title="LinguaTale AI", version="0.2.0", lifespan=lifespan)

class StoryAnalysisRequest(BaseModel):
    story: str
    source_language: str = "auto"

@app.get("/health")
async def health() -> dict[str, str]:
    return {"service": "linguatale-ai", "status": "UP"}

@app.post("/api/v1/analyze")
async def analyze(request: StoryAnalysisRequest) -> dict:
    return await provider.analyze(request.story)