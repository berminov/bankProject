package ru.mts.bankProject.store.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mts.bankProject.store.entities.DepositTypeEntity;
@Repository
public interface DepositTypeRepository extends JpaRepository<DepositTypeEntity, Integer> {
}
