package com.example.transfer.controller;

import com.example.transfer.dto.DrawOutRequestParamDTO;
import com.example.transfer.dto.RechargeRequestParamDTO;
import com.example.transfer.dto.TransferRequestParamDTO;
import com.example.transfer.entity.Transfer;
import com.example.transfer.response.ResultBody;
import com.example.transfer.service.def.TransferService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/mock")
public class SimsBankController {

    @Resource
    private TransferService transferService;

    @RequestMapping(value = "/recharge/v1", method = RequestMethod.POST)
    public ResultBody<Transfer> recharge(@RequestBody RechargeRequestParamDTO rechargeRequestParamDTO) {
        Transfer transfer = transferService.recharge(rechargeRequestParamDTO);
        return new ResultBody<>(transfer);
    }

    @RequestMapping(value = "/drawOut/v1", method = RequestMethod.POST)
    public ResultBody<Transfer> drawOut(@RequestBody DrawOutRequestParamDTO drawOutRequestParamDTO) {
        Transfer transfer = transferService.drawOut(drawOutRequestParamDTO);
        return new ResultBody<>(transfer);
    }

    @RequestMapping(value = "/transfer/v1", method = RequestMethod.POST)
    public ResultBody<Transfer> transfer(@RequestBody TransferRequestParamDTO transferParamDTO) {
        Transfer transfer = transferService.transfer(transferParamDTO);
        return new ResultBody<>(transfer);
    }
}
