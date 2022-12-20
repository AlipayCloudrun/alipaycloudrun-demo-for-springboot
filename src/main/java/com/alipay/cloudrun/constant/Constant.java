/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.cloudrun.constant;

/**
 * 系统常量
 */
public class Constant {
    /**
     * 服务ID
     *
     */
    public static final String APP_ID = System.getenv("CLOUDRUN_PAASCORE_APPID");
    /**
     * 私钥
     *
     */
    public static final String PRIVATE_KEY = System.getenv("OSS_PRIVATE_KEY");
    /**
     * 公钥
     *
     */
    public static final String PUBLIC_KEY = System.getenv("OSS_PUBLIC_KEY");
    /**
     * 环境ID
     *
     */
    public static final String CLOUD_ID = System.getenv("CLOUDRUN_PAASCORE_ENVID");
    /**
     * 服务网关
     *
     */
    public static final String SERVER = "https://openapi.alipay.com/gateway.do";
}
