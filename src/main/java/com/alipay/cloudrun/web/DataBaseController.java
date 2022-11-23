package com.alipay.cloudrun.web;

import com.alibaba.fastjson.JSON;
import com.alipay.cloudrun.aop.annotation.ControllerPointCut;
import com.alipay.cloudrun.dao.RecordDAO;
import com.alipay.cloudrun.web.request.RecordInfo;
import com.alipay.cloudrun.web.response.Result;
import com.alipay.cloudrun.web.response.ResultCodeEnum;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试db访问
 */
@Log4j2
@RestController
@RequestMapping("/database")
public class DataBaseController {

    @Autowired
    private RecordDAO recordDAO;

    /**
     * 获取记录列表
     *
     * @return
     */
    @ControllerPointCut
    @GetMapping("/getList")
    public Result<List<RecordInfo>> getList() {
        List<RecordInfo> userList = null;
        try {
            userList = recordDAO.getList();
        } catch (Throwable e) {
            log.error("查询数据库记录异常", e);
            return Result.fail(ResultCodeEnum.DATAACCESS_ERROR);
        }
        return Result.success(userList);
    }

    /**
     * 删除记录
     *
     * @return
     */
    @ControllerPointCut
    @GetMapping("/deleteRecord")
    public Result<Boolean> deleteRecord(@RequestParam Long id) {
        Boolean res = false;
        try {
            res = recordDAO.deleteById(id);
        } catch (Throwable e) {
            log.error("删除数据库记录异常,id=" + id, e);
            return Result.fail(ResultCodeEnum.DATAACCESS_ERROR);
        }
        return Result.success(res);
    }

    /**
     * 新增记录
     *
     * @return
     */
    @ControllerPointCut
    @PostMapping("/addRecord")
    public Result<Boolean> addRecord(@RequestBody RecordInfo recordInfo) {
        Boolean res;
        try {
            res = recordDAO.addRecord(recordInfo);
        } catch (Throwable e) {
            log.error("新增数据库记录异常,recordInfo=" + JSON.toJSONString(recordInfo), e);
            return Result.fail(ResultCodeEnum.DATAACCESS_ERROR);
        }

        return Result.success(res);
    }
}
