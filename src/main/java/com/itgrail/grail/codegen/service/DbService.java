package com.itgrail.grail.codegen.service;

import com.itgrail.grail.codegen.service.dto.DbTablesReqDTO;
import com.itgrail.grail.codegen.service.dto.DbTablesRespDTO;

/**
 * @author xujin
 * created at 2019/6/6 18:11
 **/
public interface DbService {

    public DbTablesRespDTO getDbTables(DbTablesReqDTO reqDTO);

}
