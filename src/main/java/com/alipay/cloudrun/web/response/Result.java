/*
 * Alipay.com Inc.
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.cloudrun.web.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * restful结果
 */
@Data
@RequiredArgsConstructor
public class Result<T> {

    /**
     * 是否成功
     */
    private boolean success;

    /**
     * 结果数据
     */
    private T data;

    /**
     * 错误码
     */
    private String errorCode;

    /**
     * 错误提示
     */
    private String errorMessage;


    public Result(boolean success) {
        this.success = success;
    }

    public static <T> Result<T> success() {
        return new Result<>(true);
    }

    public static <T> Result<T> fail() {
        return new Result<>(false);
    }

    public static <T> Result<T> success(T data) {
        Result<T> response = new Result<>(true);
        response.setData(data);
        return response;
    }

    public static <T> Result<T> fail(ResultCodeEnum resultCodeEnum) {
        Result<T> result = new Result<>(false);
        result.setErrorCode(result.getErrorCode());
        result.setErrorMessage(result.getErrorMessage());
        return result;
    }

    public static <T> Result<T> error(ResultCodeEnum resultCodeEnum){
        Result<T> result = new Result<>(false);
        result.setErrorCode(resultCodeEnum.getResultCode());
        result.setErrorMessage(resultCodeEnum.getResultMessage());
        return result;
    }

    public static <T> Result<T> fail(T data) {
        Result<T> response = new Result<>(false);
        response.setData(data);
        return response;
    }

}
