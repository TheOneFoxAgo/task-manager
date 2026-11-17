from psycopg import sql
from typing import TypeVar, Generic, Optional, Type, Dict, Any

from repositories.postgresql.connection import PostgresConnection
from repositories.base.repository import Repository
#from repositories.interfaces 

T = TypeVar("T")
ID = TypeVar("ID")

class PostgresRepository(Repository[T, ID], Generic[T, ID]):
    def __init__(
        self, 
        db: PostgresConnection,
        table_name: str,
        model_cls: Type[T],
        pk_column: str,
    ):
        self._db = db
        self.table_name = table_name
        self.model_cls = model_cls
        self.pk_column = pk_column

    def _to_dict(self, entity: T) -> Dict[str, Any]:
        return {
            key: value
            for key, value in vars(entity).items()
        }
    def _to_entity(self, row: Dict[str, Any]) -> T:
        return self.model_cls(**row)

    def create(self, entity: T) -> ID:
        data = self._to_dict(entity)
        #убираем PK чтобы не было NULL в запросе для pk
        data.pop(self.pk_column, None)

        columns = list(data.keys())
        values = list(data.values())

        query = sql.SQL(
            "INSERT INTO {table} ({fields}) VALUES ({placeholders}) RETURNING {pk}"
        ).format(
            table=sql.Identifier(self.table_name),
            fields=sql.SQL(", ").join(map(sql.Identifier, columns)),
            placeholders=sql.SQL(", ").join(sql.Placeholder() * len(columns)),
            pk=sql.Identifier(self.pk_column),
        )
        with self._db.get_connection() as conn:
            with conn.cursor() as cur:
                cur.execute(query, values)
                row = cur.fetchone()
                return row[self.pk_column]
        
    def read(self, entity_id: ID) -> Optional[T]:
            query = sql.SQL(
                "SELECT * FROM {table} WHERE {pk} = %s"
            ).format(
                table=sql.Identifier(self.table_name),
                pk=sql.Identifier(self.pk_column),
            )
            with self._db.get_connection() as conn:
                with conn.cursor() as cur:
                    cur.execute(query, (entity_id,))
                    row = cur.fetchone()
                    return self._to_entity(row) if row else None
                
    def update(self, entity: T) -> None:
        data = self._to_dict(entity)
        entity_id = data.pop(self.pk_column, None)
        if entity_id is None:
            raise ValueError(f"Невозможно обновить запись без первичного ключа '{self.pk_column}'")
        if not data:
            return
        set_placeholders = [
            sql.SQL("{col} = %s").format(col=sql.Identifier(col))
            for col in data.keys()
        ]
        values = list(data.values()) + [entity_id]
        query = sql.SQL("UPDATE {table} SET {sets} WHERE {pk} = %s").format(
            table=sql.Identifier(self.table_name),
            sets=sql.SQL(", ").join(set_placeholders),
            pk=sql.Identifier(self.pk_column),
        )
        with self._db.get_connection() as conn:
            with conn.cursor() as cur:
                cur.execute(query, values)
            
    def delete(self, entity_id: ID) -> bool:
        query = sql.SQL("DELETE FROM {table} WHERE {pk} = %s").format(
            table=sql.Identifier(self.table_name),
            pk=sql.Identifier(self.pk_column),
        )

        with self._db.get_connection() as conn:
            with conn.cursor() as cur:
                cur.execute(query, (entity_id,))
                return cur.rowcount > 0