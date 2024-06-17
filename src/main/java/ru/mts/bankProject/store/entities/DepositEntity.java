package ru.mts.bankProject.store.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "deposits")
public class DepositEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private BigDecimal amount;

    private LocalDate startDate;

    private LocalDate endDate;

    private BigDecimal depositRate;

    @ManyToOne
    @JoinColumn(name = "deposit_type_id", referencedColumnName = "id")
    private DepositTypeEntity depositType;

    @ManyToOne
    @JoinColumn(name = "interest_payment_type_id", referencedColumnName = "id")
    private InterestPaymentTypeEntity interestPaymentType;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private CustomerEntity customer;

    @ManyToOne
    @JoinColumn(name = "deposit_account_id", referencedColumnName = "id")
    private AccountEntity bankAccount;

}
