/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.cloudrun.web.response;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文件信息
 */
@Data
@NoArgsConstructor
public class FileInfo {
    /**
     * 文件id
     */
    private String fileId;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 文件路径
     */
    private String absolutePath;

    /**
     * 文件下载地址
     */
    private String downloadUrl;

    /**
     * 文件类型
     */
    private String fileType;

}
