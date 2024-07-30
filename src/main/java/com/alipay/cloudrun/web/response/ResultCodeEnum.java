/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.cloudrun.web.response;

/**
 * 错误码枚举
 */
public enum ResultCodeEnum {
    /**
     * 成功
     */
    SUCCESS("SUCCESS", "成功"),

    /**
     * 数据库异常
     */
    DATAACCESS_ERROR("DATAACCESS_ERROE", "数据库异常,请检查db连接"),

    /**
     * OSS异常
     */
    OSS_ERROR("OSS_ERROR", "OSS异常"),

    /**
     * 弹性伸缩入参异常
     */
    ELASTIC_SCALE_PARAM_ERROR("ELASTIC_SCALE_PARAM_ERROR", "弹性伸缩入参异常，请保证是整数并且在0-80之间"),

    /**
     * 缓存异常
     */
    REDIS_ERROR("REDIS_ERROR", "缓存异常,请检查Redis配置");

    /**
     * 错误码
     */
    private final String resultCode;

    /**
     * 错误信息
     */
    private final String resultMessage;

    /**
     * 创建一个ErrorCodeEnum
     *
     * @param errorCode    错误码
     * @param errorMessage 错误信息
     */
    private ResultCodeEnum(String errorCode, String errorMessage) {
        this.resultCode = errorCode;
        this.resultMessage = errorMessage;
    }

    /**
     * Getter method for property <tt>resultCode</tt>.
     *
     * @return property value of resultCode
     */
    public String getResultCode() {
        return resultCode;
    }

    /**
     * Getter method for property <tt>resultMessage</tt>.
     *
     * @return property value of resultMessage
     */
    public String getResultMessage() {
        return resultMessage;
    }
}
