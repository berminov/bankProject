package ru.mts.bankProject.store.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customers")
public class CustomerEntity {

    @Id
    private int id;

    private String phoneNumber;

    private String password;

    @OneToOne
    @JoinColumn(name = "bank_account_id",  referencedColumnName = "id")
    private BankAccountEntity bankAccount;

    @OneToMany(mappedBy = "customer")
    private List<RequestEntity> requests;

    @OneToMany(mappedBy = "customer")
    private List<DepositEntity> deposits;
}
