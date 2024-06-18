package ru.mts.bankProject.store.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mts.bankProject.store.entities.DepositEntity;

import java.time.LocalDate;
import java.util.List;
@Repository
public interface DepositRepository extends JpaRepository<DepositEntity, Integer> {
    List<DepositEntity> findByCustomerId(int id);

    List<DepositEntity> findByNextPaymentDate(LocalDate date);

    List<DepositEntity> findByCustomerIdAndActiveTrue(int customerId);

    List<DepositEntity> findByCustomerIdAndActiveFalse(int customerId);
}
