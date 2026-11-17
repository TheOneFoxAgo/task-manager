import json
from pathlib import Path

class AdminAuthHandler:
    def __init__(self, config_path: Path):
        self._config_path = config_path
    def login(self, login: str, password: str) -> bool:
        if not self._config_path.exists():
            return False
        with open(self._config_path, "r", encoding="utf-8") as cfg:
            config = json.load(cfg)
        for admin in config.get("admins", []):
            if admin.get("login") == login and admin.get("password") == password:
                return True
        return False