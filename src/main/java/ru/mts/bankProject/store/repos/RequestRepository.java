package ru.mts.bankProject.store.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mts.bankProject.store.entities.RequestEntity;

public interface RequestRepository extends JpaRepository<RequestEntity, Integer> {
}
