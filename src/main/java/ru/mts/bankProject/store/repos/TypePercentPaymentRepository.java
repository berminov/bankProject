package ru.mts.bankProject.store.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mts.bankProject.store.entities.TypePercentPaymentEntity;

public interface TypePercentPaymentRepository extends JpaRepository<TypePercentPaymentEntity, Integer> {
}
