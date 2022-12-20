/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.cloudrun.web;

import com.alipay.cloudrun.aop.annotation.ControllerPointCut;
import com.alipay.cloudrun.web.response.Result;
import com.alipay.cloudrun.web.response.ResultCodeEnum;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * redis缓存
 */
@Log4j2
@RestController
@RequestMapping("/redis")
public class RedisTestController {

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 获取key对应value
     *
     * @return
     */
    @ControllerPointCut
    @GetMapping("/get")
    public Result<Object> getValue(@RequestParam String key) {
        String s = "";
        try {
            s = redisTemplate.opsForValue().get(key);
        } catch (Throwable e) {
            log.error("Redis异常", e);
            return Result.fail(ResultCodeEnum.REDIS_ERROR.getResultCode());
        }
        return Result.success(s);
    }


    /**
     * 设置kv
     *
     * @return
     */
    @ControllerPointCut
    @GetMapping("/set")
    public Result<String> setValueForKey(@RequestParam String key, @RequestParam String value) {
        try {
            redisTemplate.opsForValue().set(key, value);
        } catch (Throwable e) {
            log.error("Redis异常", e);
            return Result.fail(ResultCodeEnum.REDIS_ERROR.getResultCode());
        }
        return Result.success("true");
    }
}
