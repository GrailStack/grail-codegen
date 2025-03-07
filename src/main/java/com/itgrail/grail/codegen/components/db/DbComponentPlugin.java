package com.itgrail.grail.codegen.components.db;

import com.alibaba.fastjson.JSONObject;
import com.itgrail.grail.codegen.components.db.common.DbModelConverter;
import com.itgrail.grail.codegen.components.db.exceptions.DbException;
import com.itgrail.grail.codegen.core.plugin.ComponentPlugin;
import com.itgrail.grail.codegen.utils.FileUtil;
import com.itgrail.grail.codegen.utils.ResourceUtil;
import com.itgrail.grail.codegen.components.db.model.Table;
import com.itgrail.grail.codegen.template.datamodel.CodeGenDataModel;
import com.itgrail.grail.codegen.template.engine.FreemarkerTemplateEngine;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

/**
 * @author xujin
 * created at 2019/6/4 21:32
 **/
@Component
@Slf4j
public class DbComponentPlugin implements ComponentPlugin {

    private static final String doFile = "${db.table.doName}.java.ftl";
    private static final String daoFile = "${db.table.daoName}.java.ftl";
    private static final String coFile = "${db.table.coName}.java.ftl";
    private static final String controllerFile = "${db.table.controllerName}.java.ftl";
    private static final String serviceFile = "${db.table.serviceName}.java.ftl";
    private static final String serviceImplFile = "${db.table.serviceImplName}.java.ftl";

    @Override
    public boolean canHandleDir(Resource dir, CodeGenDataModel model) {
        return false;
    }

    @Override
    public void handleDir(Resource dir, File toDir, CodeGenDataModel model) {

    }

    @Override
    public boolean canHandleFile(Resource file, CodeGenDataModel model) {
        return isDaoTemplate(file) || isDoTemplate(file) || isTypeTemplate(file, coFile) || isTypeTemplate(file, controllerFile) || isTypeTemplate(file, serviceFile) || isTypeTemplate(file, serviceImplFile) || isCmdTemplate(file);
    }

    @Override
    public void handleFile(Resource file, File toDir, CodeGenDataModel model) throws IOException {
        DbDataModel dbDataModel = retrieveDbDataModel(model);
        if (dbDataModel == null) {
            return;
        }
        List<Table> tables = dbDataModel.getTables();
        for (Table table : tables) {
            dbDataModel.setTable(table);
            String result = new FreemarkerTemplateEngine().process(model, ResourceUtil.getResourceUrlName(file));
            //忽略空文件
            if (StringUtils.isNotBlank(result)) {
                if (isDoTemplate(file)) {
                    FileUtil.writeToFile(result, new File(toDir, table.getDoName() + ".java"));
                } else if(isDaoTemplate(file)) {
                    FileUtil.writeToFile(result, new File(toDir, table.getDaoName() + ".java"));
                }else if(isTypeTemplate(file, coFile)) {
                    FileUtil.writeToFile(result, new File(toDir, table.getCoName() + ".java"));
                }else if(isTypeTemplate(file, controllerFile)) {
                    FileUtil.writeToFile(result, new File(toDir, table.getControllerName() + ".java"));
                }else if(isTypeTemplate(file, serviceFile)) {
                    FileUtil.writeToFile(result, new File(toDir, table.getServiceName() + ".java"));
                }else if(isTypeTemplate(file, serviceImplFile)) {
                    FileUtil.writeToFile(result, new File(toDir, table.getServiceImplName() + ".java"));
                }else if(isCmdTemplate(file)) {
                    FileUtil.writeToFile(result, new File(toDir, file.getFilename().replace("${db.table.name}", DbModelConverter.tableNameToCamelCase(table.getTableName())).replace(".ftl","")));
                }
            }
        }
    }

    protected boolean isCmdTemplate(Resource file) {
        try {
            return ResourceUtil.getResourceName(file).contains("${db.table.name}Cmd");
        } catch (Exception ex) {
            return false;
        }
    }

    protected boolean isTypeTemplate(Resource file, String type) {
        try {
            return type.equalsIgnoreCase(ResourceUtil.getResourceName(file));
        } catch (Exception ex) {
            return false;
        }
    }

    protected boolean isDoTemplate(Resource file) {
        try {
            return doFile.equalsIgnoreCase(ResourceUtil.getResourceName(file));
        } catch (Exception ex) {
            return false;
        }
    }

    protected boolean isDaoTemplate(Resource file) {
        try {
            return daoFile.equalsIgnoreCase(ResourceUtil.getResourceName(file));
        } catch (Exception ex) {
            return false;
        }
    }

    protected DbDataModel retrieveDbDataModel(CodeGenDataModel model) {
        try {
            Field dbField = model.getClass().getDeclaredField("db");
            dbField.setAccessible(true);
            return (DbDataModel) dbField.get(model);
        } catch (Exception ex) {
            log.error(String.format("database插件处理失败，model=%s", JSONObject.toJSONString(model)), ex);
            throw new DbException("database插件处理失败");
        }
    }

}
