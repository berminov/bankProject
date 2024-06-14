package ru.mts.bankProject.store.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "bank_accounts")
public class BankAccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private BigDecimal numBankAccount;

    private BigDecimal money;

    @OneToOne(mappedBy = "bankAccount")
    private CustomerEntity customer;

    @OneToMany(mappedBy = "bankAccount")
    private List<DepositEntity> deposits;

}
