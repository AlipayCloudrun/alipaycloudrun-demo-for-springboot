/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.cloudrun.web;

import com.alipay.cloudrun.aop.annotation.ControllerPointCut;
import com.alipay.cloudrun.client.SimpleFeignClient;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试服务访问
 */
@RestController
public class HttpTestController {

    /**
     * LOGGER
     */
    private static final Logger LOGGER = LoggerFactory.getLogger("BIZOPS-MVC-LOGGER");

    @Autowired
    private SimpleFeignClient simpleFeignClient;

    /**
     * request
     */
    @Autowired
    private HttpServletRequest request;

    @ControllerPointCut
    @GetMapping("/service")
    public String service() {
        return simpleFeignClient.service2();
    }

    @ControllerPointCut
    @GetMapping("/service2")
    public String service2() {
        return "服务调用成功!TimeStamp is" + System.currentTimeMillis();
    }
}
