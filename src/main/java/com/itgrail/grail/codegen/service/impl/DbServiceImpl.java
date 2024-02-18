package com.itgrail.grail.codegen.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Preconditions;
import com.itgrail.grail.codegen.components.db.database.DBProperties;
import com.itgrail.grail.codegen.components.db.database.Database;
import com.itgrail.grail.codegen.components.db.database.DatabaseFactory;
import com.itgrail.grail.codegen.service.DbService;
import com.itgrail.grail.codegen.service.dto.*;
import com.itgrail.grail.codegen.utils.CommonUtil;
import com.itgrail.grail.codegen.utils.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * @author xujin
 * created at 2019/6/6 18:13
 **/
@Slf4j
@Service
public class DbServiceImpl implements DbService {

    @Value("${ploutos.server.url:http://localhost:8080}")
    private String ploutosURl;

    @Override
    public DbTablesRespDTO getDbTables(DbTablesReqDTO reqDTO) {
        checkParams(reqDTO);
        DbTablesRespDTO respDTO = new DbTablesRespDTO();

        DBProperties dbProperties = new DBProperties();
        dbProperties.setDbType(reqDTO.getDbType()).setDbUrl(reqDTO.getDbUrl());
        dbProperties.setDbUserName(reqDTO.getDbUserName()).setDbPassword(reqDTO.getDbPassword());
        Database database = null;
        try {
            database = DatabaseFactory.create(dbProperties);
            List<String> allTableNames = database.getAllTableNames();
            respDTO.setDbName(database.getDbName());
            respDTO.setTables(allTableNames);
            return respDTO;
        } finally {
            CommonUtil.closeClosable(database);
        }
    }

    @Override
    public DbInfoTablesRespDTO getDbTables(String sourceIdentifier) {
        String restUrl = ploutosURl+"/openapi/mysql/getDBbyResourceId?resourceId=" + sourceIdentifier;
        Database database = null;
        DbInfoTablesRespDTO respDTO = null;
        DBProperties dbProperties = null;
        try {
            respDTO = new DbInfoTablesRespDTO();
            String dbStr = HttpClientUtil.get(restUrl);
            PloutosResDto checkResult = JSON.parseObject(dbStr, PloutosResDto.class);
            DbTablesResDto dbTablesResDto = checkResult.getData();
            dbProperties = new DBProperties();
            dbProperties.setDbType("MySql").setDbUrl(dbTablesResDto.getDbUrl());
            dbProperties.setDbUserName(dbTablesResDto.getDbUserName())
                    .setDbPassword(dbTablesResDto.getDbPassword());
            DbTablesReqDTO dbInfo = new DbTablesReqDTO();
            dbInfo.setDbType("MySql");
            dbInfo.setDbUrl(dbTablesResDto.getDbUrl());
            dbInfo.setDbUserName(dbTablesResDto.getDbUserName());
            dbInfo.setDbPassword(dbTablesResDto.getDbPassword());
            respDTO.setDbInfo(dbInfo);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        try {
            log.info("------start query table--------");
            database = DatabaseFactory.create(dbProperties);
            List<String> allTableNames = database.getAllTableNames();
            respDTO.setDbName(database.getDbName());
            respDTO.setTables(allTableNames);
        }
        finally {
            CommonUtil.closeClosable(database);
        }
        return respDTO;
    }

    protected void checkParams(DbTablesReqDTO reqDTO) {
        Preconditions.checkArgument(Objects.nonNull(reqDTO), "入参不能为空");
        Preconditions.checkArgument(StringUtils.isNoneBlank(reqDTO.getDbType()), "dbType不能为空");
        Preconditions.checkArgument(StringUtils.isNoneBlank(reqDTO.getDbUrl()), "dbUrl不能为空");
        Preconditions.checkArgument(StringUtils.isNoneBlank(reqDTO.getDbUserName()), "dbUserName不能为空");
        Preconditions.checkArgument(StringUtils.isNoneBlank(reqDTO.getDbPassword()), "dbPassword不能为空");
    }

}
