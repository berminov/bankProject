package ru.mts.bankProject.store.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mts.bankProject.store.entities.InterestPaymentTypeEntity;
@Repository
public interface InterestPaymentTypeRepository extends JpaRepository<InterestPaymentTypeEntity, Integer> {
}
