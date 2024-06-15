package ru.mts.bankProject.store.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "deposits")
public class DepositEntity {

    @Id
    private int id;

    private boolean depositRefill;

    private BigDecimal depositAmount;

    private Date startDate;

    private Date endDate;

    private BigDecimal depositRate;

    private int percentPaymentAccountId;

    private Date percentPaymentDate;

    private boolean capitalization;

    private int depositRefundAccountId;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private CustomerEntity customer;

    @ManyToOne
    @JoinColumn(name = "deposit_account_id", referencedColumnName = "id")
    private AccountEntity bankAccount;

}
