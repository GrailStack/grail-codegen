package com.itgrail.grail.codegen.web;

import com.itgrail.grail.codegen.service.CodeGenService;
import com.itgrail.grail.codegen.service.dto.DomainTypeDTO;
import com.itgrail.grail.codegen.service.dto.GenCodeTemplateRespDTO;
import com.itgrail.grail.codegen.utils.IOUtil;
import com.itgrail.grail.codegen.service.dto.*;
import com.itgrail.grail.codegen.service.dto.AppTypeDTO;
import com.itgrail.grail.dto.ResultData;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;


/**
 * @author xujin
 * created at 2019/5/25 16:15
 **/
@RestController
//@Api("生成代码模板")
@RequestMapping("/codegen")
public class CodeGenController {

    @Autowired
    private CodeGenService codeGenService;

    @RequestMapping(path = "/metadata/{templateName}", method = RequestMethod.GET)
    //@ApiOperation(value = "获取模板元数据信息", httpMethod = "GET")
    public ResultData getMetaData(@PathVariable String templateName) {
        return codeGenService.getMetaData(templateName);
    }

    @RequestMapping(path = "/metadata/appType", method = RequestMethod.GET)
    //@ApiOperation(value = "获取应用类型", httpMethod = "GET")
    public ResultData<List<AppTypeDTO>> getAppTypeList() {
        return ResultData.success(codeGenService.getAppTypeList());
    }

    @RequestMapping(path = "/metadata/queryAllDomain", method = RequestMethod.GET)
   // @ApiOperation(value = "获取业务域", httpMethod = "GET")
    public ResultData<List<DomainTypeDTO>> queryAllDomain() {
        return ResultData.success(codeGenService.queryAllDomain());
    }

    @RequestMapping(path = "/download", method = RequestMethod.POST)
   // @ApiOperation(value = "下载模板", httpMethod = "POST")
    public void downloadTemplate(@RequestBody Map<String, Object> reqDTO, HttpServletResponse response) throws IOException {
        ResultData resultData = codeGenService.genCodeTemplate(reqDTO);
        if (resultData.getMsgCode().equals("400")) {
            response.setContentType("application/json; charset=utf-8");
            IOUtil.writeAsJson(response.getOutputStream(), resultData);
        } else {
            response.setContentType("application/octet-stream; charset=utf-8");
            GenCodeTemplateRespDTO respDTO=(GenCodeTemplateRespDTO)resultData.getData();
            response.addHeader("Content-Disposition", String.format("attachment;filename=%s.zip", respDTO.getFileName()));
            IOUtil.writeBytes(response.getOutputStream(), respDTO.getFileBytes());
        }
        response.flushBuffer();
    }

}
