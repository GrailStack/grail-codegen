package com.itgrail.grail.codegen.service;

import com.itgrail.grail.codegen.service.dto.AppTypeDTO;
import com.itgrail.grail.codegen.service.dto.DomainTypeDTO;
import com.itgrail.grail.dto.ResultData;

import java.util.List;
import java.util.Map;

/**
 * @author xujin
 * created at 2019/5/25 17:52
 **/
public interface CodeGenService {

    public ResultData getMetaData(String templateName);

    public ResultData genCodeTemplate(Map<String, Object> req);

    public List<AppTypeDTO> getAppTypeList();

    public List<DomainTypeDTO> queryAllDomain();
}
