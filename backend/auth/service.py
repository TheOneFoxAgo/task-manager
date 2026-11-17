from pathlib import Path
from auth.auth_admin import AdminAuthHandler

class AuthenticationService:
    def __init__(self, config_path: str | Path = "backend/config.json"):
        path = Path(config_path)
        self._admin_handler = AdminAuthHandler(path)
    def login_admin(self, login: str, password: str) -> bool:
        return self._admin_handler.login(login, password)