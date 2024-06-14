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
@Table(name = "deposits_types")
public class DepositTypeEntity {

    @Id
    private int id;

    private String depositTypeName;

    @OneToMany(mappedBy = "depositType")
    private List<DepositEntity> deposits;

}
