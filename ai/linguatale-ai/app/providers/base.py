from abc import ABC, abstractmethod

class TranslationProvider(ABC):
    @abstractmethod
    async def translate(self, text: str, source_language: str, target_language: str) -> str: raise NotImplementedError

class TTSProvider(ABC):
    @abstractmethod
    async def synthesize(self, text: str, language: str, voice: str) -> bytes: raise NotImplementedError

class LLMProvider(ABC):
    @abstractmethod
    async def analyze(self, text: str) -> dict: raise NotImplementedError