package ru.mts.bankProject.store.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mts.bankProject.store.entities.CurrentRequestStatusEntity;

public interface CurrentRequestStatusRepository extends JpaRepository<CurrentRequestStatusEntity, Integer> {
}
