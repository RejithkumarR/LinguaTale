import asyncio
import aio_pika
from app.settings import settings
from app.orchestration.pipeline import StoryPipeline

async def consume() -> None:
    connection = await aio_pika.connect_robust(settings.rabbitmq_url)
    channel = await connection.channel()
    queue = await channel.declare_queue(settings.generation_queue, durable=True)
    pipeline = StoryPipeline()
    async with queue.iterator() as messages:
        async for message in messages:
            async with message.process(requeue=False):
                await pipeline.run(message.body.decode())

async def start_worker() -> asyncio.Task:
    return asyncio.create_task(consume())