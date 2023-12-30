package com.itgrail.grail.codegen.service.impl;

import com.google.common.base.Preconditions;
import com.itgrail.grail.codegen.components.db.database.DBProperties;
import com.itgrail.grail.codegen.components.db.database.Database;
import com.itgrail.grail.codegen.components.db.database.DatabaseFactory;
import com.itgrail.grail.codegen.service.DbService;
import com.itgrail.grail.codegen.service.dto.DbTablesReqDTO;
import com.itgrail.grail.codegen.service.dto.DbTablesRespDTO;
import com.itgrail.grail.codegen.utils.CommonUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author xujin
 * created at 2019/6/6 18:13
 **/
@Service
public class DbServiceImpl implements DbService {

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

    protected void checkParams(DbTablesReqDTO reqDTO) {
        Preconditions.checkArgument(Objects.nonNull(reqDTO), "入参不能为空");
        Preconditions.checkArgument(StringUtils.isNoneBlank(reqDTO.getDbType()), "dbType不能为空");
        Preconditions.checkArgument(StringUtils.isNoneBlank(reqDTO.getDbUrl()), "dbUrl不能为空");
        Preconditions.checkArgument(StringUtils.isNoneBlank(reqDTO.getDbUserName()), "dbUserName不能为空");
        Preconditions.checkArgument(StringUtils.isNoneBlank(reqDTO.getDbPassword()), "dbPassword不能为空");
    }

}
