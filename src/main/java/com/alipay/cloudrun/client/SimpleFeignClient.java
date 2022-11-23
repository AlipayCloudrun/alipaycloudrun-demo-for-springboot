/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.cloudrun.client;

import com.alipay.cloudrun.aop.annotation.SalPointCut;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 微服务模版
 */
@FeignClient(name = "feignClient", url = "${feign.url}")
public interface SimpleFeignClient {

    @SalPointCut
    @GetMapping("/service2")
    String service2();
}