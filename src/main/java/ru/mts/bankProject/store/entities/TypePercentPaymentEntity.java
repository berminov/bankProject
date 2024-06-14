package ru.mts.bankProject.store.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "types_percent_payment")
public class TypePercentPaymentEntity {

    @Id
    private int id;

    private String typePercentPaymentPeriod;

    @OneToMany(mappedBy = "typePercentPayment")
    private List<DepositEntity> deposits;

}
