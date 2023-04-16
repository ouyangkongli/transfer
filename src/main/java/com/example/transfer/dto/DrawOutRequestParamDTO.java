package com.example.transfer.dto;

import lombok.Data;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class DrawOutRequestParamDTO {
    /**
     * 交易单号
     */
    @NotEmpty(message = "交易单号不能为空")
    private String orderNo;

    /**
     * 交易金额
     */
    @NotNull(message = "交易金额不能为空")
    @Digits(integer = 10, fraction = 2, message = "整数部分不超过10位,小数点后不超过2位")
    private BigDecimal amount;

    /**
     * 转入账号
     */
    @NotEmpty(message = "取现账号不能为空")
    private String payerAccount;
}
