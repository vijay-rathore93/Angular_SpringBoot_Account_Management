package org.accountmanagement.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Builder
@Entity
@Table(name = "account")
@AllArgsConstructor
@NoArgsConstructor
public class Account extends Auditable<String> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer accountId;
    @NotNull(message = "Account number can not empty..")
    @Column(unique = true)
    private Integer accountNumber;


    @Transient
    private String customerName;

    private Integer customerId;
    @NotEmpty(message = "Bank Name can not empty..")
    private String bankName;
    @NotEmpty(message = "IFSC Code can not empty..")
    private String ifscCode;
    @NotEmpty(message = "Branch Code can not empty..")
    private String branchCode;
    private String bankAddress;
    private Double totalAmount;
    private Boolean isActive;
}
