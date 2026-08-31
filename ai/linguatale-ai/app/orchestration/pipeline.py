import httpx
from app.settings import settings
from app.providers.openai_provider import OpenAIProvider
from app.storage import AudioStorage

class StoryPipeline:
    def __init__(self) -> None:
        self.provider = OpenAIProvider()
        self.storage = AudioStorage()

    async def run(self, job_id: str) -> None:
        async with httpx.AsyncClient(timeout=60) as client:
            job = (await client.get(f"{settings.java_api_url}/api/v1/jobs/{job_id}")).json()
            story = (await client.get(f"{settings.java_api_url}/api/v1/stories/{job['storyId']}")).json()
        await self.callback(job_id, "PROCESSING", 15, job["storyId"], job["targetLanguage"], None, None)
        translated = await self.provider.translate(story["content"], story["sourceLanguage"], job["targetLanguage"])
        await self.callback(job_id, "TRANSLATING", 45, job["storyId"], job["targetLanguage"], None, None)
        audio = await self.provider.synthesize(translated, job["targetLanguage"], job["voice"])
        key = f"stories/{job['storyId']}/{job['targetLanguage']}/{job_id}.mp3"
        self.storage.put_audio(key, audio)
        await self.callback(job_id, "COMPLETED", 100, job["storyId"], job["targetLanguage"], key, "audio/mpeg")

    async def callback(self, job_id: str, status: str, progress: int, story_id: int, language: str, audio_key: str | None, content_type: str | None) -> None:
        payload = {"status": status, "progress": progress, "error": None, "storyId": story_id, "language": language, "audioKey": audio_key, "contentType": content_type}
        async with httpx.AsyncClient(timeout=30) as client:
            response = await client.post(f"{settings.java_api_url}/api/v1/internal/jobs/{job_id}/callback", headers={"X-LinguaTale-Secret": settings.ai_callback_secret}, json=payload)
            response.raise_for_status()