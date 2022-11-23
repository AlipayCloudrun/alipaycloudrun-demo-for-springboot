/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.cloudrun.web.request;

import java.math.BigInteger;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author xieer
 * @version : UserInfo.java, v 0.1 2022-11-10 7:58 下午 xieer Exp $$
 */
@Data
@AllArgsConstructor
public class RecordInfo {
    /**
     * 标定Id，自增
     */
    private long id;
    /**
     * 创建时间
     */
    private Date gmtCreate;
    /**
     * 修改时间
     */
    private Date gmtModified;

    /**
     * record
     */
    private String record;

}
