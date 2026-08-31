import httpx
from .base import LLMProvider, TranslationProvider, TTSProvider
from app.settings import settings

class OpenAIProvider(LLMProvider, TranslationProvider, TTSProvider):
    def __init__(self) -> None:
        self.headers = {"Authorization": f"Bearer {settings.openai_api_key}", "Content-Type": "application/json"}

    async def _response(self, prompt: str) -> str:
        payload = {"model": settings.openai_model, "input": prompt}
        async with httpx.AsyncClient(timeout=120) as client:
            response = await client.post("https://api.openai.com/v1/responses", headers=self.headers, json=payload)
            response.raise_for_status()
            data = response.json()
        return data.get("output_text") or "".join(part.get("text", "") for item in data.get("output", []) for part in item.get("content", []) if part.get("type") in {"output_text", "text"})

    async def analyze(self, text: str) -> dict:
        result = await self._response("Analyze this story. Return a concise JSON object with language, scene_count and characters. Story:\n" + text)
        return {"raw": result}

    async def translate(self, text: str, source_language: str, target_language: str) -> str:
        return await self._response(f"Translate the following story from {source_language} to {target_language}. Preserve meaning, names, dialogue and paragraph structure. Return only the translation.\n\n{text}")

    async def synthesize(self, text: str, language: str, voice: str) -> bytes:
        payload = {"model": settings.openai_tts_model, "voice": voice, "input": text, "response_format": "mp3"}
        async with httpx.AsyncClient(timeout=180) as client:
            response = await client.post("https://api.openai.com/v1/audio/speech", headers=self.headers, json=payload)
            response.raise_for_status()
            return response.content