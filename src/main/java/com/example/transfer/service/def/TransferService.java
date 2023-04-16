package com.example.transfer.service.def;

import com.example.transfer.dto.DrawOutRequestParamDTO;
import com.example.transfer.dto.RechargeRequestParamDTO;
import com.example.transfer.dto.TransferRequestParamDTO;
import com.example.transfer.entity.Transfer;

public interface TransferService {

    /**
     * 充值
     *
     * @param rechargeRequestParamDTO 充值
     * @return Transfer
     */
    Transfer recharge(RechargeRequestParamDTO rechargeRequestParamDTO);

    /**
     * 提现
     *
     * @param drawOutRequestParamDTO 提现参数
     * @return Transfer
     */
    Transfer drawOut(DrawOutRequestParamDTO drawOutRequestParamDTO);

    /**
     * 转账接口
     *
     * @param paramDTO 转账请求参数
     * @return Transfer
     */
    Transfer transfer(TransferRequestParamDTO paramDTO);


}
