package com.itgrail.grail.codegen.template.custom;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedCaseInsensitiveMap;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

/**
 * @author xujin
 * created at 2019/5/24 20:36
 **/
@Component
public class CustomizationCenter {

    @Autowired
    private List<CustomizedTemplate> customizedTemplateList;

    private Map<String, CustomizedTemplate> customizedTemplateMap;

    public boolean containsTemplate(String templateName) {
        return customizedTemplateMap.containsKey(templateName);
    }

    public boolean containsTemplateSubmodule(String templateName, String subModule) {
        if (!containsTemplate(templateName)) {
            return false;
        }
        CustomizedTemplate template = customizedTemplateMap.get(templateName);
        return template.getSubModules().contains(subModule);
    }

    public CustomizedTemplate getTemplate(String templateName) {
        return customizedTemplateMap.get(templateName);
    }

    @PostConstruct
    public void init() {
        customizedTemplateMap = new LinkedCaseInsensitiveMap();
        if (CollectionUtils.isNotEmpty(customizedTemplateList)) {
            for (CustomizedTemplate template : customizedTemplateList) {
                customizedTemplateMap.put(template.getTemplateName(), template);
            }
        }
    }

}
