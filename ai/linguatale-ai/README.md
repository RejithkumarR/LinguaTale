# LinguaTale AI Service

Python/FastAPI foundation for AI orchestration.

## Responsibilities

- Story analysis
- Translation orchestration
- Narration planning
- Text-to-speech orchestration
- Audio processing orchestration

Provider-specific implementations must sit behind the interfaces in `app/providers/base.py`.

## Run

```bash
python -m venv .venv
source .venv/bin/activate
pip install -r requirements.txt
uvicorn app.main:app --reload --port 8000
```

Windows PowerShell activation:

```powershell
.\.venv\Scripts\Activate.ps1
```
