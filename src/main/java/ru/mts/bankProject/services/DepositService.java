package ru.mts.bankProject.services;

import org.springframework.stereotype.Service;
import ru.mts.bankProject.store.entities.DepositEntity;
import ru.mts.bankProject.store.repos.DepositRepository;

import java.util.List;

@Service
public class DepositService {

    private final DepositRepository depositRepository;

    public DepositService(DepositRepository depositRepository) {
        this.depositRepository = depositRepository;
    }

    public DepositEntity getDepositById(int id){
        return depositRepository.findById(id).orElse(null);
    }

    public List<DepositEntity> getCustomerDeposits(int id){
        return depositRepository.findByCustomerId(id);
    }
}
