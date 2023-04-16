package com.example.transfer.dao;

import com.example.transfer.entity.Account;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class AccountDao {
    private static final Map<String, Account> accountMap = new ConcurrentHashMap<>();

    public Account create(Account account) {
        synchronized (this) {
            if (accountMap.get(account.getAccountNo()) != null) {
                throw new RuntimeException("账户已存在");
            }
            accountMap.put(account.getAccountNo(), account);
            return account;
        }
    }

    public Account update(Account account, BigDecimal sourceAmount) {
        // compare and swap
        Account sourceAccount = accountMap.get(account.getAccountNo());
        if (sourceAccount.getAvailableAmount().equals(sourceAmount)) {
            accountMap.put(account.getAccountNo(), account);
            return account;
        } else {
            throw new RuntimeException("并发操作失败");
        }
    }

    public Account loadForUpdate(String accountNo) {
        // 加分布式锁
        return cloneAccount(accountMap.get(accountNo));
    }

    public Account load(String accountNo) {
        Account account = accountMap.get(accountNo);
        return cloneAccount(account);
    }

    private static Account cloneAccount(Account account) {
        if (account == null) {
            return null;
        }
        Account result = new Account();
        BeanUtils.copyProperties(account, result);
        return result;
    }

    public List<Account> listAccount() {
        return new ArrayList<>(accountMap.values());
    }
}
