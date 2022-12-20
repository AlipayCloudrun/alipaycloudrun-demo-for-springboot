/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.cloudrun.dao;

import com.alipay.api.response.AlipayOpenMiniCloudFileDeleteResponse;
import com.alipay.api.response.AlipayOpenMiniCloudFileQueryResponse;
import com.alipay.api.response.AlipayOpenMiniCloudFileUploadResponse;
import com.alipay.api.response.AlipayOpenMiniCloudFilelistQueryResponse;
import java.util.List;

/**
 * OSS服务
 */
public interface OssService {
    /**
     * 上传文件
     *
     * @param name     文件名称
     * @param filePath 文件路径
     * @param folder   云存储文件夹
     * @return AlipayOpenMiniCloudFileUploadResponse 文件上传返回信息
     */
    AlipayOpenMiniCloudFileUploadResponse uploadFile(String name, String filePath, String folder);

    /**
     * 文件列表查询
     *
     * @param folder 云存储文件夹
     * @return AlipayOpenMiniCloudFilelistQueryResponse 文件列表返回信息
     */
    AlipayOpenMiniCloudFilelistQueryResponse queryFileList(String folder);

    /**
     * 删除文件
     *
     * @param folder  云存储文件夹
     * @param picList 文件名列表
     */
    AlipayOpenMiniCloudFileDeleteResponse deleteFile(String folder, List<String> picList);

    /**
     * 查询文件详情
     *
     * @param fileId 文件ID
     * @return AlipayOpenMiniCloudFileQueryResponse 文件查询返回信息
     */
    AlipayOpenMiniCloudFileQueryResponse getFileInfo(String fileId);
}
