package com.example.transfer.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultBody<T> implements Serializable {

    private static final long serialVersionUID = -1880947384141462399L;

    private boolean success;

    /**
     * data
     */
    private T data;

    /**
     * 业务受理失败的原因
     */
    private String errorMessage;

    public ResultBody(T result) {
        this.success = true;
        this.data = result;
    }

    public ResultBody(RuntimeException e) {
        this.success = false;
        this.errorMessage = e.getMessage();
    }
}
