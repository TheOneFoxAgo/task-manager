import psycopg
from psycopg.rows import dict_row

class PostgresConnection:
    def __init__(self, dsn: str):
        self._dsn = dsn
    def get_connection(self):
        return psycopg.connect(self._dsn, row_factory=dict_row)
