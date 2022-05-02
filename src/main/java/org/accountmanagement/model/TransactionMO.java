package org.accountmanagement.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class TransactionMO {
    private Integer fromAccount;
    private Integer toAccount;
    private Long amountTobeDeposit;
    private Long amountToBeWithDraw;
}
