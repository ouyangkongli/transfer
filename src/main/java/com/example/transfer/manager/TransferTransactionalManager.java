package com.example.transfer.manager;

import com.example.transfer.dao.AccountDao;
import com.example.transfer.dao.TransferDao;
import com.example.transfer.entity.Account;
import com.example.transfer.entity.Transfer;
import com.example.transfer.enums.TransferStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;

@Component
public class TransferTransactionalManager {
    @Resource
    private AccountDao accountDao;
    @Resource
    private TransferDao transferDao;

    @Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED)
    public Transfer rechargeTransactional(Transfer transfer) {
        Account payeeAccount = accountDao.load(transfer.getPayeeAccount());
        if (payeeAccount == null) {
            transfer.setFailureMsg("充值账户不存在");
            transfer.setStatus(TransferStatus.FAILED);
            transferDao.update(transfer);
            throw new RuntimeException("充值账户不存在");
        }
        payeeAccount = accountDao.loadForUpdate(payeeAccount.getAccountNo());
        BigDecimal payeeSourceAmount = payeeAccount.getAvailableAmount();
        BigDecimal payeeAvailableAmount = payeeSourceAmount.add(transfer.getAmount());
        payeeAccount.setAvailableAmount(payeeAvailableAmount);
        accountDao.update(payeeAccount, payeeSourceAmount);
        transfer.setStatus(TransferStatus.SUCCESS);
        transfer.setTimeSuccess(System.currentTimeMillis());
        transferDao.update(transfer);
        return transfer;
    }


    @Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED)
    public Transfer drawOutTransactional(Transfer transfer) {
        Account payerAccount = accountDao.load(transfer.getPayerAccount());
        if (payerAccount == null) {
            transfer.setFailureMsg("提现账户不存在");
            transfer.setStatus(TransferStatus.FAILED);
            transferDao.update(transfer);
            throw new RuntimeException("提现账户不存在");
        }
        if (transfer.getAmount().compareTo(payerAccount.getAvailableAmount()) > 0) {
            transfer.setFailureMsg("提现账户余额不足");
            transfer.setStatus(TransferStatus.FAILED);
            transferDao.update(transfer);
            throw new RuntimeException("提现账户余额不足");
        }
        payerAccount = accountDao.loadForUpdate(payerAccount.getAccountNo());
        BigDecimal payerSourceAmount = payerAccount.getAvailableAmount();
        BigDecimal payerAvailableAmount = payerSourceAmount.subtract(transfer.getAmount());
        payerAccount.setAvailableAmount(payerAvailableAmount);
        accountDao.update(payerAccount, payerSourceAmount);
        transfer.setStatus(TransferStatus.SUCCESS);
        transfer.setTimeSuccess(System.currentTimeMillis());
        transferDao.update(transfer);
        return transfer;
    }

    @Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED)
    public Transfer transferTransactional(Transfer transfer) {
        Account payerAccount = accountDao.load(transfer.getPayerAccount());
        if (payerAccount == null) {
            transfer.setFailureMsg("转出账户不存在");
            transfer.setStatus(TransferStatus.FAILED);
            transferDao.update(transfer);
            throw new RuntimeException("转出账户不存在");
        }
        if (transfer.getAmount().compareTo(payerAccount.getAvailableAmount()) > 0) {
            transfer.setFailureMsg("转出账户余额不足");
            transfer.setStatus(TransferStatus.FAILED);
            transferDao.update(transfer);
            throw new RuntimeException("转出账户余额不足");
        }
        Account payeeAccount = accountDao.load(transfer.getPayeeAccount());
        if (payeeAccount == null) {
            transfer.setFailureMsg("转入账户不存在");
            transfer.setStatus(TransferStatus.FAILED);
            transferDao.update(transfer);
            throw new RuntimeException("转入账户不存在");
        }
        if (payerAccount.getId() > payeeAccount.getId()) {
            payeeAccount = accountDao.loadForUpdate(payeeAccount.getAccountNo());
            BigDecimal payeeSourceAmount = payeeAccount.getAvailableAmount();
            BigDecimal payeeAvailableAmount = payeeSourceAmount.add(transfer.getAmount());
            payeeAccount.setAvailableAmount(payeeAvailableAmount);
            accountDao.update(payeeAccount, payeeSourceAmount);
            payerAccount = accountDao.loadForUpdate(payerAccount.getAccountNo());
            BigDecimal payerSourceAmount = payerAccount.getAvailableAmount();
            BigDecimal payerAvailableAmount = payerSourceAmount.subtract(transfer.getAmount());
            payerAccount.setAvailableAmount(payerAvailableAmount);
            accountDao.update(payerAccount, payerSourceAmount);
        } else {
            payerAccount = accountDao.loadForUpdate(payerAccount.getAccountNo());
            BigDecimal payerSourceAmount = payerAccount.getAvailableAmount();
            BigDecimal payerAvailableAmount = payerSourceAmount.subtract(transfer.getAmount());
            payerAccount.setAvailableAmount(payerAvailableAmount);
            accountDao.update(payerAccount, payerSourceAmount);
            payeeAccount = accountDao.loadForUpdate(payeeAccount.getAccountNo());
            BigDecimal payeeSourceAmount = payeeAccount.getAvailableAmount();
            BigDecimal payeeAvailableAmount = payeeSourceAmount.add(transfer.getAmount());
            payeeAccount.setAvailableAmount(payeeAvailableAmount);
            accountDao.update(payeeAccount, payeeSourceAmount);
        }
        transfer.setStatus(TransferStatus.SUCCESS);
        transfer.setTimeSuccess(System.currentTimeMillis());
        transferDao.update(transfer);
        return transfer;
    }


}
