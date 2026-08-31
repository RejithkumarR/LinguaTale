from fastapi import FastAPI
from pydantic import BaseModel

app = FastAPI(title="LinguaTale AI", version="0.1.0")


class StoryAnalysisRequest(BaseModel):
    story: str
    source_language: str = "auto"


class StoryAnalysisResponse(BaseModel):
    language: str
    scenes: int
    status: str


@app.get("/health")
async def health() -> dict[str, str]:
    return {"service": "linguatale-ai", "status": "UP"}


@app.post("/api/v1/analyze", response_model=StoryAnalysisResponse)
async def analyze(request: StoryAnalysisRequest) -> StoryAnalysisResponse:
    # Provider/model integration will be added behind the AI orchestration layer.
    return StoryAnalysisResponse(
        language=request.source_language,
        scenes=1 if request.story.strip() else 0,
        status="READY",
    )
