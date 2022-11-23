/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.cloudrun.dao;

import com.alipay.cloudrun.aop.annotation.DalPointCut;
import com.alipay.cloudrun.web.request.RecordInfo;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 记录信息数据库操作实现
 */
@Service
public class RecordDAOImpl implements RecordDAO {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @DalPointCut
    @Override
    public List<RecordInfo> getList() {
        String sql = "select id,gmt_create,gmt_modified,record from record_info order by id desc limit 10";
        List<Map<String, Object>> result = jdbcTemplate.queryForList(sql);
        return result.stream().map(item ->
                        new RecordInfo(((BigInteger) item.get("id")).longValue(), (Date) item.get("gmt_create"),
                                (Date) item.get("gmt_modified"), (String) item.get("record")))
                .collect(Collectors.toList());
    }

    @DalPointCut
    @Override
    public Boolean addRecord(RecordInfo recordInfo) {
        if (StringUtils.isEmpty(recordInfo.getRecord())) {
            throw new IllegalArgumentException("参数不可为空");
        }
        String sql = "insert into record_info (record) values (?)";
        return jdbcTemplate.update(sql, recordInfo.getRecord()) == 1;
    }

    @DalPointCut
    @Override
    public Boolean deleteById(Long id) {
        String sql = "delete from record_info where id = ?";
        return jdbcTemplate.update(sql, id) == 1;
    }
}
