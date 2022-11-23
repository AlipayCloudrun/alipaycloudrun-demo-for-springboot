/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.cloudrun.aop;

import com.alibaba.fastjson.JSON;
import com.alipay.cloudrun.web.response.Result;
import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 日志拦截切面
 */
@Aspect
@Component
public class ControllerAspect {
    private static final Logger logger = LoggerFactory.getLogger("MVC-LOGGER");

    /**
     * 设置切入点 记录操作记录 在注解的位置切入代码
     */
    @Pointcut("@annotation(com.alipay.cloudrun.aop.annotation.ControllerPointCut)")
    public void logPointcut() {
    }


    @Around("logPointcut()")
    public Object doAround(ProceedingJoinPoint joinPoint) {
        boolean res = true;
        Object obj = null;
        long begin = System.currentTimeMillis();
        try {
            obj = joinPoint.proceed();
            if (obj instanceof Result && ((Result<?>) obj).getData() != null) {
                res = ((Result<?>) obj).isSuccess();
            } else {
                res = false;
            }
        } catch (Throwable t) {
            logger.error("controller aspect fail:", t);
            res = false;
        } finally {
            long rt = System.currentTimeMillis() - begin;
            long threadID = Thread.currentThread().getId();
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            String call = request.getRequestURI();
            String str = JSON.toJSONString(Arrays.asList(joinPoint.getArgs()));
            String msg = String.format("[%d],[%s],[%dms],[%s],%s", threadID, call, rt, res ? "Y" : "N", str);

            if (logger.isInfoEnabled()) {
                logger.info(msg);
            }
        }
        return obj;
    }

}

