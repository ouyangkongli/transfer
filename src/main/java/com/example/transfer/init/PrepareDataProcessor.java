package com.example.transfer.init;

import com.alibaba.fastjson.JSON;
import com.example.transfer.dao.AccountDao;
import com.example.transfer.entity.Account;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;

@Service
public class PrepareDataProcessor {
    private final AccountDao accountDao;

    public PrepareDataProcessor(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @PostConstruct
    public void init() {
        BufferedReader bufferedReader;
        StringBuilder stringBuffer = new StringBuilder();
        try {
            bufferedReader = new BufferedReader(new FileReader(ResourceUtils.getFile("classpath:accounts.json")));
            String temp;
            while ((temp = bufferedReader.readLine()) != null) {
                stringBuffer.append(temp);
            }
            List<Account> accountList = JSON.parseArray(stringBuffer.toString(), Account.class);
            for (Account account : accountList) {
                accountDao.create(account);
            }
            bufferedReader.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
