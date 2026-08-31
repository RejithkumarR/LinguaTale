import boto3
from botocore.client import Config
from app.settings import settings

class AudioStorage:
    def __init__(self) -> None:
        self.client = boto3.client("s3", endpoint_url=settings.s3_endpoint_url, aws_access_key_id=settings.s3_access_key, aws_secret_access_key=settings.s3_secret_key, region_name=settings.s3_region, config=Config(signature_version="s3v4"))
        self._ensure_bucket()
    def _ensure_bucket(self) -> None:
        try: self.client.head_bucket(Bucket=settings.s3_bucket)
        except self.client.exceptions.ClientError: self.client.create_bucket(Bucket=settings.s3_bucket)
    def put_audio(self, key: str, content: bytes) -> None:
        self.client.put_object(Bucket=settings.s3_bucket, Key=key, Body=content, ContentType="audio/mpeg")