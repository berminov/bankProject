package ru.mts.bankProject.store.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mts.bankProject.store.entities.RequestStatusEntity;

public interface RequestStatusRepository extends JpaRepository<RequestStatusEntity, Integer> {
}
