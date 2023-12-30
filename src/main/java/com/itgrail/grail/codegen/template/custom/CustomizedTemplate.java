package com.itgrail.grail.codegen.template.custom;

import com.google.common.collect.Lists;
import com.itgrail.grail.codegen.core.TemplateProcessor;
import com.itgrail.grail.codegen.template.datamodel.CodeGenDataModel;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class CustomizedTemplate {

    @Autowired
    private TemplateProcessor templateProcessor;

    private static final String TEMPLATE_LOCATE_BASE_PATH = "templates/";

    public GenResult gen(Map<String, Object> req) throws IllegalArgumentException {
        CodeGenDataModel dataModel = convert(req);
        byte[] fileBytes = templateProcessor.process(dataModel);
        return new GenResult().setFileBytes(fileBytes).setFileName(genFileName(dataModel));
    }

    public abstract String getTemplateName();

    public abstract List<ModuleMetaData> initSubModules();

    public abstract List<String> getSubModules();

    public abstract ModuleMetaData getParentModule();


    public abstract String getTemplateLocatePath();

    public String getDefaultGenFileName() {
        return "init-" + getTemplateName().toLowerCase();
    }

    public boolean hasSubModel(String subModel) {
        String subModelTmp = subModel.replaceFirst("-", "");
        return Optional.ofNullable(getSubModules()).orElse(Lists.newArrayList())
                .stream().anyMatch(e -> e.equalsIgnoreCase(subModelTmp));
    }

    public String getBasePath() {
        return TEMPLATE_LOCATE_BASE_PATH;
    }

    public abstract TemplateMetaData getTemplateMetadata();

    public abstract CodeGenDataModel convert(Map<String, Object> request) throws IllegalArgumentException;

    public abstract String genFileName(CodeGenDataModel dataModel);

}

