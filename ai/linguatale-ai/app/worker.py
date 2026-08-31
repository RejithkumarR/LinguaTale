import asyncio
import aio_pika
import httpx
from app.settings import settings
from app.orchestration.pipeline import StoryPipeline

async def consume() -> None:
    connection = await aio_pika.connect_robust(settings.rabbitmq_url)
    channel = await connection.channel()
    queue = await channel.declare_queue(settings.generation_queue, durable=True)
    pipeline = StoryPipeline()
    async with queue.iterator() as messages:
        async for message in messages:
            job_id = message.body.decode()
            async with message.process(requeue=False):
                try:
                    await pipeline.run(job_id)
                except Exception as exc:
                    async with httpx.AsyncClient(timeout=30) as client:
                        job_response = await client.get(f"{settings.java_api_url}/api/v1/jobs/{job_id}")
                        if job_response.is_success:
                            job = job_response.json()
                            await pipeline.callback(job_id, "ERROR", 0, job["storyId"], job["targetLanguage"], None, None)
                    print(f"LinguaTale job {job_id} failed: {exc}")

async def start_worker() -> asyncio.Task:
    return asyncio.create_task(consume())