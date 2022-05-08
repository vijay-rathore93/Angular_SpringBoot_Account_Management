package org.accountmanagement.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@Entity
@Builder
@Table(name = "transaction")
@NoArgsConstructor
@AllArgsConstructor
public class Transaction extends Auditable<String> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer txId;

    @NotNull
    private double amountTobeTransferred;
    @NotNull
    private Integer fromAccount;

    private Integer toAccount;

    private Boolean isActive;

    private LocalDate transactionDate;
}
