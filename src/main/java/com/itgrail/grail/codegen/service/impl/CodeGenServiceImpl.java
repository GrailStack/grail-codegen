package com.itgrail.grail.codegen.service.impl;

import com.itgrail.grail.codegen.core.AppTypeEnum;
import com.itgrail.grail.codegen.core.DomainTypeEnum;
import com.itgrail.grail.codegen.service.CodeGenService;
import com.itgrail.grail.codegen.service.dto.AppTypeDTO;
import com.itgrail.grail.codegen.service.dto.DomainTypeDTO;
import com.itgrail.grail.codegen.service.dto.GenCodeTemplateRespDTO;
import com.itgrail.grail.codegen.template.custom.CustomizationCenter;
import com.itgrail.grail.codegen.template.custom.CustomizedTemplate;
import com.itgrail.grail.codegen.template.custom.GenResult;
import com.itgrail.grail.dto.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author xujin
 * created at 2019/5/25 17:52
 **/
@Service
public class CodeGenServiceImpl implements CodeGenService {

    @Autowired
    private CustomizationCenter customizationCenter;

    @Override
    public ResultData getMetaData(String templateName) {
        CustomizedTemplate template = customizationCenter.getTemplate(templateName);
        if (template == null) {
            return ResultData.fail("模板不存在");
        }
        return ResultData.success(template.getTemplateMetadata());
    }

    @Override
    public ResultData<GenCodeTemplateRespDTO> genCodeTemplate(Map<String, Object> reqDTO) {
        GenCodeTemplateRespDTO respDTO = new GenCodeTemplateRespDTO();
        if (reqDTO == null) {
            return ResultData.fail("400", "reqDTO不能为空");
        }
        if (reqDTO.get("templateName") == null) {
            return ResultData.fail("400", "templateName不能为空");
        }
        if (!customizationCenter.containsTemplate(reqDTO.get("templateName").toString())) {
            return ResultData.fail("400", "模板不存在");
        }
        GenResult genResult = null;
        try {
            CustomizedTemplate template = customizationCenter.getTemplate(reqDTO.get("templateName").toString());
            genResult = template.gen(reqDTO);
        } catch (IllegalArgumentException iex) {
            return ResultData.fail("400", iex.getMessage());
        } catch (Exception ex) {
            return ResultData.fail("400", ex.getMessage());
        }
        if (genResult.getFileBytes() == null) {
            return ResultData.fail("400", "模板创建失败");
        }
        respDTO.setFileBytes(genResult.getFileBytes()).setFileName(genResult.getFileName());
        return ResultData.success(respDTO);
    }

    @Override
    public List<AppTypeDTO> getAppTypeList() {
        return AppTypeEnum.getAppType();
    }

    @Override
    public List<DomainTypeDTO> queryAllDomain() {
        return DomainTypeEnum.queryAllDomain();
    }


}
