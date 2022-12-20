/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.cloudrun.web;

import com.alibaba.fastjson.JSON;
import com.alipay.api.domain.FileBaseInfoResponse;
import com.alipay.api.response.AlipayOpenMiniCloudFileQueryResponse;
import com.alipay.api.response.AlipayOpenMiniCloudFileUploadResponse;
import com.alipay.api.response.AlipayOpenMiniCloudFilelistQueryResponse;
import com.alipay.cloudrun.aop.annotation.ControllerPointCut;
import com.alipay.cloudrun.dao.OssService;
import com.alipay.cloudrun.web.response.FileInfo;
import com.alipay.cloudrun.web.response.Result;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 对象存储测试controller
 */
@Log4j2
@RestController
public class OSSTestController {

    @Autowired
    OssService ossService;

    /**
     * 上传文件
     *
     * @param file 文件
     * @return Result
     */
    @ControllerPointCut
    @PostMapping("/file/upload")
    public Result<AlipayOpenMiniCloudFileUploadResponse> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        log.info("upload file");
        AlipayOpenMiniCloudFileUploadResponse response = null;
        try {
            if (file != null) {
                String fileName = file.getOriginalFilename();
                String fileSuffix = fileName.substring(fileName.lastIndexOf("."));
                String randomFileName = new Date().getTime() + fileSuffix;
                String filePath = "/tmp/upload" + randomFileName;
                File targetFile = new File(filePath);
                file.transferTo(targetFile);
                response = ossService.uploadFile(randomFileName, filePath, "/cloudruntest");
                targetFile.delete();
            }
            return Result.success(response);
        } catch (Throwable t) {
            log.error("upload file error:", t);
            return Result.fail(response);
        }
    }

    /**
     * 获取文件列表
     *
     * @param folder 文件夹
     * @return Result
     */
    @ControllerPointCut
    @GetMapping("/file/list")
    public Result<List<FileInfo>> getFileList(@RequestParam String folder) {
        System.out.println(folder);

        log.info("get file list from:" + folder);
        AlipayOpenMiniCloudFilelistQueryResponse response = null;
        List<FileInfo> lists = new ArrayList<>();
        List<FileBaseInfoResponse> fileBaseInfoResponseList = new ArrayList<>();
        try {
            response = ossService.queryFileList(folder != null ? folder : "/cloudruntest");
            log.info("get file list:" + JSON.toJSONString(response));
            fileBaseInfoResponseList = response.getFileBaseInfoResponseList();
            if (fileBaseInfoResponseList == null) {
                return Result.success(lists);
            }
            for (FileBaseInfoResponse baseInfoResponse : fileBaseInfoResponseList) {
                FileInfo fileInfo = new FileInfo();
                fileInfo.setFileId(baseInfoResponse.getFileId());
                fileInfo.setFileName(baseInfoResponse.getFileName());
                fileInfo.setAbsolutePath(baseInfoResponse.getAbsolutePath());
                fileInfo.setFileType(baseInfoResponse.getFileType());
                AlipayOpenMiniCloudFileQueryResponse resp = ossService.getFileInfo(baseInfoResponse.getFileId());
                fileInfo.setDownloadUrl(resp.getDownloadUrl());
                lists.add(fileInfo);
                log.info("get file list:" + JSON.toJSONString(fileInfo));
            }
            return Result.success(lists);
        } catch (Throwable t) {
            log.error("getFileList error:", t);
            return Result.fail(lists);
        }

    }

    /**
     * 查询文件详情
     *
     * @param fileId 文件ID
     * @return Result
     */
    @ControllerPointCut
    @GetMapping("/file/info")
    public Result<AlipayOpenMiniCloudFileQueryResponse> getFileInfo(@RequestParam String fileId) {
        log.info("get file info");
        AlipayOpenMiniCloudFileQueryResponse response = null;
        try {
            response = ossService.getFileInfo(fileId);
            return Result.success(response);
        } catch (Throwable t) {
            log.error("getFileInfo error:", t);
            return Result.fail(response);
        }
    }

    /**
     * 删除文件
     *
     * @param folder   文件夹
     * @param fileName 文件名
     * @return Result
     */
    @ControllerPointCut
    @SneakyThrows
    @PostMapping("/file/delete")
    public Result<String> delete(@RequestParam String folder, @RequestParam String fileName) {
        try {
            log.info("delete file list from:" + folder + "," + fileName);
            List<String> picList = new ArrayList<>();
            picList.add(fileName);
            ossService.deleteFile(folder, picList);
            return Result.success();
        } catch (Throwable t) {
            log.error("getFileList error:", t);
            return Result.fail();
        }
    }
}
