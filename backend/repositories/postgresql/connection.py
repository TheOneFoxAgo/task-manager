import psycopg
from psycopg.rows import dict_row
from contextlib import contextmanager
from typing import Iterator
from psycopg_pool import ConnectionPool
class PostgresConnection:
    def __init__(self, dsn: str, min_size: int = 1, max_size: int = 10):
        self._pool = ConnectionPool(
            conninfo=dsn,
            min_size=min_size,
            max_size=max_size,
            kwargs={"row_factory": dict_row},
            open=True
        )
    @contextmanager
    def get_connection(self) -> Iterator[psycopg.Connection]:
        with self._pool.connection() as conn:
            yield conn
    def close(self) -> None:
        self._pool.close()
