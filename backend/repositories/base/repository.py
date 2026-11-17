from abc import ABC, abstractmethod
from typing import Generic, Optional, TypeVar

T = TypeVar("T")
ID = TypeVar("ID")

class Repository(ABC, Generic[T, ID]):
    @abstractmethod
    def create(self, entity: T) -> ID:
        pass
    @abstractmethod
    def read(self, entity_id: ID) -> Optional[T]:
        pass
    @abstractmethod
    def update(self, entity: T) -> None:
        pass
    @abstractmethod
    def delete(self, entity_id: ID) -> bool:
        pass