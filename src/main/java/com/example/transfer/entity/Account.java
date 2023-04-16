package com.example.transfer.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account implements Serializable {

    private static final long serialVersionUID = -886692187140121384L;

    /**
     * id
     */
    private Long id;

    /**
     * 账号
     */
    private String accountNo;

    /**
     * 可用金额
     */
    private BigDecimal availableAmount;

    /**
     * 账户创建时间
     */
    private Long timeCreated;
}
