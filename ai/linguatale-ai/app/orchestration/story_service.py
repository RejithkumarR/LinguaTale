from app.providers.base import LLMProvider, TranslationProvider, TTSProvider


class StoryService:
    """Coordinates story analysis, translation and narration providers."""

    def __init__(
        self,
        llm: LLMProvider,
        translator: TranslationProvider,
        tts: TTSProvider,
    ) -> None:
        self.llm = llm
        self.translator = translator
        self.tts = tts

    async def analyze(self, story: str) -> dict:
        return await self.llm.analyze(story)
