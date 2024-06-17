package ru.mts.bankProject.store.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mts.bankProject.store.entities.InterestPaymentTypeEntity;

public interface InterestPaymentTypeRepository extends JpaRepository<InterestPaymentTypeEntity, Integer> {
}
