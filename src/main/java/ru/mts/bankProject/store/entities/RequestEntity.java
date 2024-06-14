package ru.mts.bankProject.store.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "requests")
public class RequestEntity {

    @Id
    private int id;

    private Date requestDate;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private CustomerEntity customer;

    @OneToOne
    @JoinColumn(name = "deposit_id", referencedColumnName = "id")
    private DepositEntity deposit;
}
