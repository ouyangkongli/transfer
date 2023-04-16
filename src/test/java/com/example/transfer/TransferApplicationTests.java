package com.example.transfer;

import com.alibaba.fastjson2.JSONObject;
import com.example.transfer.controller.SimsBankController;
import com.example.transfer.dto.DrawOutRequestParamDTO;
import com.example.transfer.dto.RechargeRequestParamDTO;
import com.example.transfer.dto.TransferRequestParamDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
class TransferApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testRecharge() throws Exception {
        String url = "/mock/recharge/v1";
        RechargeRequestParamDTO paramDTO = new RechargeRequestParamDTO();
        paramDTO.setOrderNo(UUID.randomUUID().toString());
        paramDTO.setPayeeAccount("800001");
        paramDTO.setAmount(new BigDecimal("100.00"));
        mockMvc.perform(MockMvcRequestBuilders.post(url)
                        .content(JSONObject.toJSONString(paramDTO))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().json("{\"success\":true}"))
                .andReturn();
    }

    @Test
    public void testDrawOut() throws Exception {
        String url = "/mock/drawOut/v1";
        DrawOutRequestParamDTO paramDTO = new DrawOutRequestParamDTO();
        paramDTO.setOrderNo(UUID.randomUUID().toString());
        paramDTO.setPayerAccount("800002");
        paramDTO.setAmount(new BigDecimal("100.00"));
        mockMvc.perform(MockMvcRequestBuilders.post(url)
                        .content(JSONObject.toJSONString(paramDTO))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().json("{\"success\":true}"))
                .andReturn();

    }

    @Test
    public void testTransfer() throws Exception {
        String url = "/mock/transfer/v1";
        TransferRequestParamDTO paramDTO = new TransferRequestParamDTO();
        paramDTO.setOrderNo(UUID.randomUUID().toString());
        paramDTO.setPayerAccount("800003");
        paramDTO.setPayeeAccount("800002");
        paramDTO.setAmount(new BigDecimal("300.00"));
        mockMvc.perform(MockMvcRequestBuilders.post(url)
                        .content(JSONObject.toJSONString(paramDTO))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().json("{\"success\":true}"))
                .andReturn();

    }
}
