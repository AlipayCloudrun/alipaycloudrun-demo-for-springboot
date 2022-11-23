/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.cloudrun.dao;

import com.alipay.cloudrun.web.request.RecordInfo;
import java.util.List;

/**
 * 记录信息相关接口
 */

public interface RecordDAO {
    /**
     * 获取数据库前10条记录
     *
     * @return
     */
    List<RecordInfo> getList();


    /**
     * 添加记录
     *
     * @param recordInfo
     * @return
     */
    Boolean addRecord(RecordInfo recordInfo);

    /**
     * 删除记录
     *
     * @param id
     */
    Boolean deleteById(Long id);
}
