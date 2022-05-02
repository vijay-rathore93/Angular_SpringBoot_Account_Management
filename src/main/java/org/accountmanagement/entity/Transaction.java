package org.accountmanagement.entity;


import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Entity
@Table(name = "transaction")
public class Transaction extends Auditable<String> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer txId;
    @NotNull
    private Integer fromCustomerId;
    @NotNull
    private double amountTobeTransferred;
    @NotNull
    private Integer fromAccount;
    @NotNull
    private Integer toAccount;
    @NotNull
    private Boolean isActive;
}
