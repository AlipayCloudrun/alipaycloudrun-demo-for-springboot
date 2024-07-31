package com.alipay.cloudrun.dao;

/**
 * 弹性伸缩服务
 */
public interface ElasticScalingService {
    /**
     * 更改CPU使用率
     */
    void cpuUpdate(int percentage) throws Exception;

    /**
     * 清理CPU使用率
     */
    void cpuClean();

    /**
     *  获取CPU使用率
     */
    void getCpu();
}
