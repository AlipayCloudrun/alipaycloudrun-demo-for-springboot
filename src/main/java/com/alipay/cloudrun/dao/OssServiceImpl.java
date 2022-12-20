/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.cloudrun.dao;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.FileItem;
import com.alipay.api.request.AlipayOpenMiniCloudFileDeleteRequest;
import com.alipay.api.request.AlipayOpenMiniCloudFileQueryRequest;
import com.alipay.api.request.AlipayOpenMiniCloudFileUploadRequest;
import com.alipay.api.request.AlipayOpenMiniCloudFilelistQueryRequest;
import com.alipay.api.response.AlipayOpenMiniCloudFileDeleteResponse;
import com.alipay.api.response.AlipayOpenMiniCloudFileQueryResponse;
import com.alipay.api.response.AlipayOpenMiniCloudFileUploadResponse;
import com.alipay.api.response.AlipayOpenMiniCloudFilelistQueryResponse;
import com.alipay.cloudrun.aop.annotation.DalPointCut;
import com.alipay.cloudrun.constant.Constant;
import java.util.List;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

/**
 * OSS服务实现
 */
@Log4j2
@Service
public class OssServiceImpl implements OssService {

    /**
     * 文件上传
     */
    @DalPointCut
    @Override
    @SneakyThrows
    public AlipayOpenMiniCloudFileUploadResponse uploadFile(String name, String path, String folder) {
        AlipayClient alipayClient = new DefaultAlipayClient(Constant.SERVER, Constant.APP_ID, Constant.PRIVATE_KEY, "json", "GBK", Constant.PUBLIC_KEY, "RSA2");
        AlipayOpenMiniCloudFileUploadRequest request = new AlipayOpenMiniCloudFileUploadRequest();
        request.setCloudId(Constant.CLOUD_ID);
        request.setType("File");
        request.setPath(folder);
        request.setFileName(name);
        FileItem FileContent = new FileItem(path);
        request.setFileContent(FileContent);
        AlipayOpenMiniCloudFileUploadResponse response = alipayClient.execute(request);
        if (response.isSuccess()) {
            log.info("调用成功");
        } else {
            log.error("调用失败");
        }
        return response;
    }

    /**
     * 文件列表
     */
    @DalPointCut
    @Override
    @SneakyThrows
    public AlipayOpenMiniCloudFilelistQueryResponse queryFileList(String folder) {
        AlipayClient alipayClient = new DefaultAlipayClient(Constant.SERVER, Constant.APP_ID, Constant.PRIVATE_KEY, "json", "GBK", Constant.PUBLIC_KEY, "RSA2");
        AlipayOpenMiniCloudFilelistQueryRequest request = new AlipayOpenMiniCloudFilelistQueryRequest();
        JSONObject bizContent = new JSONObject();
        bizContent.put("cloud_id", Constant.CLOUD_ID);
        bizContent.put("path", folder);
        request.setBizContent(bizContent.toJSONString());
        AlipayOpenMiniCloudFilelistQueryResponse response = alipayClient.execute(request);
        if (response.isSuccess()) {
            log.info("调用成功");
        } else {
            log.error("调用失败");
        }
        return response;
    }

    /**
     * 文件删除
     */
    @DalPointCut
    @Override
    @SneakyThrows
    public AlipayOpenMiniCloudFileDeleteResponse deleteFile(String folder, List<String> picList) {
        AlipayClient alipayClient = new DefaultAlipayClient(Constant.SERVER, Constant.APP_ID, Constant.PRIVATE_KEY, "json", "GBK", Constant.PUBLIC_KEY, "RSA2");
        AlipayOpenMiniCloudFileDeleteRequest request = new AlipayOpenMiniCloudFileDeleteRequest();
        JSONObject bizContent = new JSONObject();
        bizContent.put("cloud_id", Constant.CLOUD_ID);
        bizContent.put("path", folder);
        bizContent.put("file_name_list", picList);
        request.setBizContent(bizContent.toJSONString());
        AlipayOpenMiniCloudFileDeleteResponse response = alipayClient.execute(request);
        if (response.isSuccess()) {
            log.info("删除成功");
            log.info("删除成功" + JSON.toJSONString(response));
        } else {
            log.error("调用失败");
        }
        log.info("删除" + JSON.toJSONString(response));
        return response;
    }

    /**
     * 文件信息获取
     */
    @DalPointCut
    @Override
    @SneakyThrows
    public AlipayOpenMiniCloudFileQueryResponse getFileInfo(String fileId) {
        AlipayClient alipayClient = new DefaultAlipayClient(Constant.SERVER, Constant.APP_ID, Constant.PRIVATE_KEY, "json", "GBK", Constant.PUBLIC_KEY, "RSA2");
        AlipayOpenMiniCloudFileQueryRequest request = new AlipayOpenMiniCloudFileQueryRequest();
        JSONObject bizContent = new JSONObject();
        bizContent.put("cloud_id", Constant.CLOUD_ID);
        bizContent.put("file_id", fileId);
        request.setBizContent(bizContent.toJSONString());
        AlipayOpenMiniCloudFileQueryResponse response = alipayClient.execute(request);
        if (response.isSuccess()) {
            log.info("调用成功");
        } else {
            log.error("调用失败");
        }
        return response;
    }

}
