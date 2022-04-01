package org.accountmanagement.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@Entity
@Table(name = "account")
public class Account extends Auditable<String> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "Account number can not empty..")
    private Long accountNumber;
    @NotEmpty(message = "Bank Name can not empty..")
    private String bankName;
    @NotEmpty(message = "IFSC Code can not empty..")
    private String ifscCode;
    @NotEmpty(message = "Branch Code can not empty..")
    private String branchCode;
    private String bankAddress;
    private Double totalAmount;
}
