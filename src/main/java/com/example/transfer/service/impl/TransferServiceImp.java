package com.example.transfer.service.impl;

import com.example.transfer.dao.TransferDao;
import com.example.transfer.dto.DrawOutRequestParamDTO;
import com.example.transfer.dto.RechargeRequestParamDTO;
import com.example.transfer.dto.TransferRequestParamDTO;
import com.example.transfer.entity.Transfer;
import com.example.transfer.enums.TransferStatus;
import com.example.transfer.help.ParamValidatorHelper;
import com.example.transfer.manager.TransferTransactionalManager;
import com.example.transfer.service.def.TransferService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

@Service
public class TransferServiceImp implements TransferService {

    @Resource
    private TransferDao transferDao;

    @Resource
    private TransferTransactionalManager transferTransactionalManager;

    @Override
    public Transfer recharge(RechargeRequestParamDTO rechargeRequestParamDTO) {
        ParamValidatorHelper.validate(rechargeRequestParamDTO);
        Transfer old = transferDao.load(rechargeRequestParamDTO.getOrderNo());
        if (Objects.nonNull(old)) {
            return old;
        }
        Transfer transfer = new Transfer();
        transfer.setAmount(rechargeRequestParamDTO.getAmount());
        transfer.setOrderNo(rechargeRequestParamDTO.getOrderNo());
        transfer.setPayeeAccount(rechargeRequestParamDTO.getPayeeAccount());
        transfer.setPayerAccount("");
        transfer.setTimeCreated(System.currentTimeMillis());
        transfer.setStatus(TransferStatus.PROCESSING);
        transferDao.create(transfer);
        transfer = transferTransactionalManager.rechargeTransactional(transfer);
        return transfer;
    }

    @Override
    public Transfer drawOut(DrawOutRequestParamDTO drawOutRequestParamDTO) {
        ParamValidatorHelper.validate(drawOutRequestParamDTO);
        Transfer old = transferDao.load(drawOutRequestParamDTO.getOrderNo());
        if (Objects.nonNull(old)) {
            return old;
        }
        Transfer transfer = new Transfer();
        transfer.setAmount(drawOutRequestParamDTO.getAmount());
        transfer.setOrderNo(drawOutRequestParamDTO.getOrderNo());
        transfer.setPayeeAccount("");
        transfer.setPayerAccount(drawOutRequestParamDTO.getPayerAccount());
        transfer.setTimeCreated(System.currentTimeMillis());
        transfer.setStatus(TransferStatus.PROCESSING);
        transferDao.create(transfer);
        transfer = transferTransactionalManager.drawOutTransactional(transfer);
        return transfer;
    }

    @Override
    public Transfer transfer(TransferRequestParamDTO paramDTO) {
        ParamValidatorHelper.validate(paramDTO);
        Transfer transfer = transferDao.load(paramDTO.getOrderNo());
        if (Objects.nonNull(transfer)) {
            return transfer;
        }
        transfer = generateTransfer(paramDTO);
        transferDao.create(transfer);
        transfer = transferTransactionalManager.transferTransactional(transfer);
        return transfer;
    }

    private Transfer generateTransfer(TransferRequestParamDTO paramDTO) {
        Transfer transfer = new Transfer();
        transfer.setAmount(paramDTO.getAmount());
        transfer.setOrderNo(paramDTO.getOrderNo());
        transfer.setPayeeAccount(paramDTO.getPayeeAccount());
        transfer.setPayerAccount(paramDTO.getPayerAccount());
        transfer.setTimeCreated(System.currentTimeMillis());
        transfer.setStatus(TransferStatus.PROCESSING);
        return transfer;
    }
}
