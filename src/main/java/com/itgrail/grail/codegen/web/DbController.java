package com.itgrail.grail.codegen.web;

import com.itgrail.grail.codegen.service.DbService;
import com.itgrail.grail.codegen.service.dto.DbTablesReqDTO;
import com.itgrail.grail.codegen.service.dto.DbTablesRespDTO;
import com.itgrail.grail.dto.ResultData;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xujin
 * created at 2019/6/6 18:09
 **/
@RestController
//@Api("数据库支持")
@RequestMapping("/db")
public class DbController {

    @Autowired
    private DbService dbService;

    @RequestMapping(path = "/table/all", method = RequestMethod.POST)
    //@ApiOperation(value = "查询数据库中所有表名", httpMethod = "POST")
    public ResultData<DbTablesRespDTO> getDbTables(@RequestBody DbTablesReqDTO reqDTO) {
        return ResultData.success(dbService.getDbTables(reqDTO));
    }

}
