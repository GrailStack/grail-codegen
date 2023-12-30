package com.itgrail.grail.codegen.custom.grailsingle;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.itgrail.grail.codegen.custom.grailframework.dto.GrailFrameworkGenRequest;
import com.itgrail.grail.codegen.custom.grailframework.dto.SubModuleDTO;
import com.itgrail.grail.codegen.custom.grailframework.metadata.GrailFrameworkMetadata;
import com.itgrail.grail.codegen.custom.grailframework.metadata.GrailVersionMetaData;
import com.itgrail.grail.codegen.custom.grailframework.model.DomainDataModel;
import com.itgrail.grail.codegen.custom.grailframework.model.GrailFrameworkDataModel;
import com.itgrail.grail.codegen.utils.CommonUtil;
import com.itgrail.grail.codegen.utils.PropertiesUtils;
import com.itgrail.grail.codegen.components.db.DbDataModel;
import com.itgrail.grail.codegen.components.db.database.DBProperties;
import com.itgrail.grail.codegen.components.db.database.Database;
import com.itgrail.grail.codegen.components.db.database.DatabaseFactory;
import com.itgrail.grail.codegen.components.db.database.DbMetaData;
import com.itgrail.grail.codegen.components.db.model.Table;
import com.itgrail.grail.codegen.template.custom.CustomizedTemplate;
import com.itgrail.grail.codegen.template.custom.ModuleMetaData;
import com.itgrail.grail.codegen.template.datamodel.CodeGenDataModel;
import com.itgrail.grail.codegen.template.datamodel.MavenModuleDataModel;
import com.itgrail.grail.codegen.template.datamodel.MavenProjectDataModel;
import com.itgrail.grail.codegen.template.datamodel.ModuleDependencyModel;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author xujin
 * created at 2019/5/24 20:50
 **/
@Component
public class GrailSingleTemplate extends CustomizedTemplate {

    private final List<ModuleMetaData> subModules = Lists.newArrayList();

    @Override
    public String getTemplateName() {
        return "grailsingle";
    }

    @Override
    public List<ModuleMetaData> initSubModules() {
        return subModules;
    }

    @Override
    public List<String> getSubModules() {
        return Lists.newArrayList();
    }

    @Override
    public ModuleMetaData getParentModule() {
        ModuleMetaData moduleMetaData = new ModuleMetaData();
        String json= PropertiesUtils.readJsonFile("json/"+getTemplateName()+".json");
        if(StringUtils.isNotEmpty(json)){
            List<ModuleDependencyModel> list = JSON.parseArray(json,ModuleDependencyModel.class);
            for (ModuleDependencyModel mo:list) {
                if("parent".equals(mo.getModulekey())){
                    moduleMetaData.setDependencys(mo.getDependencyModels());
                    moduleMetaData.setPackagingTypes(Lists.newArrayList("pom"));
                }

            }
        }
        return moduleMetaData;
    }

    @Override
    public String getTemplateLocatePath() {
        return getBasePath() + getTemplateName().toLowerCase();
    }

    @Override
    public GrailFrameworkMetadata getTemplateMetadata() {
        GrailFrameworkMetadata metadata = new GrailFrameworkMetadata();
        metadata.setTemplateName(getTemplateName());
        metadata.setGrailVersions(getGrailVersionMetaDataList());
        metadata.setSubModules(initSubModules());
        metadata.setParentModule(getParentModule());
        metadata.setDatabases(getDbMetaDataList());
        return metadata;
    }

    protected List<DbMetaData> getDbMetaDataList() {
        DbMetaData dbMetaData = new DbMetaData();
        dbMetaData.setDbType("MySql");
        return Lists.newArrayList(dbMetaData);
    }

    protected List<GrailVersionMetaData> getGrailVersionMetaDataList() {
        GrailVersionMetaData grailVersionMetaData = new GrailVersionMetaData();
        grailVersionMetaData.setGrailFrameworkVersion("1.0.0.RELEASE");
        grailVersionMetaData.setJavaVersion("1.8");
        grailVersionMetaData.setSpringBootVersion("2.4.1");
        grailVersionMetaData.setSpringCloudVersion("2020.0.0");

        GrailVersionMetaData grailVersionMetaData2 = new GrailVersionMetaData();
        grailVersionMetaData2.setGrailFrameworkVersion("1.0.0-SNAPSHOT");
        grailVersionMetaData2.setJavaVersion("1.8");
        grailVersionMetaData2.setSpringBootVersion("2.4.1");
        grailVersionMetaData2.setSpringCloudVersion("2020.0.0");
        return Lists.newArrayList(grailVersionMetaData, grailVersionMetaData2);
    }

    @Override
    public CodeGenDataModel convert(Map<String, Object> request) throws IllegalArgumentException {
        GrailFrameworkGenRequest genRequest = check(request);
        GrailFrameworkDataModel dataModel = new GrailFrameworkDataModel();
        dataModel.setTemplateName(genRequest.getTemplateName());
        dataModel.setGrailFrameworkVersion(genRequest.getGrailFrameworkVersion());
        dataModel.setJavaVersion("1.8");
        MavenProjectDataModel project = new MavenProjectDataModel();
        dataModel.setProject(project);
        project.setGroupId(genRequest.getGroupId());
        project.setArtifactId(genRequest.getArtifactId());
        project.setDescription(genRequest.getDescription());
        project.setBasePackage(genRequest.getBasePackage());
        if(null!=genRequest.getDbModel()){
            project.setDbConfigure(true);
        }

        //处理依赖
        List<ModuleDependencyModel> moduleDependencyModels=genRequest.getModuleDependencyModels();
        if(null==moduleDependencyModels||moduleDependencyModels.isEmpty()){
            String json=PropertiesUtils.readJsonFile("json/"+getTemplateName()+".json");
            if(StringUtils.isNotEmpty(json)) {
                List<ModuleDependencyModel> list = JSON.parseArray(json, ModuleDependencyModel.class);
                project.setDependencies(list);
            }
        }else{
            project.setDependencies(moduleDependencyModels);
        }

        project.setSubModules(Maps.newHashMap());

        if(null!=genRequest.getSubModules()){
            for (SubModuleDTO subModuleDTO : genRequest.getSubModules()) {
                String subModuleName = subModuleDTO.getSubModuleName();
                MavenModuleDataModel subModule = new MavenModuleDataModel();
                subModule.setModuleName(subModuleName);
                subModule.setArtifactId(genRequest.getArtifactId() + "-" + subModuleName);
                subModule.setGroupId(genRequest.getGroupId());
                subModule.setBasePackage(genRequest.getBasePackage() + "." + subModuleName);
                subModule.setPackaging(subModuleDTO.getPackaging());
                project.getSubModules().put(subModuleName, subModule);
            }
        }
        if (genRequest.getDomain() != null) {
            DomainDataModel domainDataModel = new DomainDataModel();
            domainDataModel.setCode(genRequest.getDomain().getCode());
            domainDataModel.setParentCode(genRequest.getDomain().getParentCode());
            domainDataModel.setName(genRequest.getDomain().getName());
            domainDataModel.setDesc(genRequest.getDomain().getDesc());
            dataModel.setDomain(domainDataModel);
        }

        if (genRequest.getDbModel() != null) {
            DbDataModel dbDataModel = new DbDataModel();
            dataModel.setDb(dbDataModel);
            DBProperties dbProperties = new DBProperties();
            dbProperties.setDbType(genRequest.getDbModel().getDbType());
            dbProperties.setDbUrl(genRequest.getDbModel().getDbUrl());
            dbProperties.setDbUserName(genRequest.getDbModel().getDbUserName());
            dbProperties.setDbPassword(genRequest.getDbModel().getDbPassword());
            Database database = DatabaseFactory.create(dbProperties);
            List<Table> tables = database.getTables(genRequest.getDbModel().getTables());
            dbDataModel.setTables(tables);
            dbDataModel.setDbName(database.getDbName());
            dbDataModel.setDbDriver(database.getDriverClassName());
            dbDataModel.setDbUrl(database.getDbUrl());
            dbDataModel.setDbUserName(database.getDbUserName());
            dbDataModel.setDbPassword(database.getDbPassword());
            CommonUtil.closeClosable(database);
        }

        return dataModel;
    }

    protected GrailFrameworkGenRequest check(Map<String, Object> request) throws IllegalArgumentException {
        Preconditions.checkNotNull(request, "request不能为空");
        Preconditions.checkNotNull(request.get("templateName"), "templateName不能为空");
        Preconditions.checkArgument(getTemplateName().equalsIgnoreCase(request.get("templateName").toString()), "templateName不一致");
        GrailFrameworkGenRequest genRequest = JSONObject.parseObject(JSONObject.toJSONString(request), GrailFrameworkGenRequest.class);
        Preconditions.checkArgument(StringUtils.isNoneBlank(genRequest.getGrailFrameworkVersion()), "grailFrameworkVersion不能为空");
        Preconditions.checkArgument(StringUtils.isNoneBlank(genRequest.getGroupId()), "groupId不能为空");
        Preconditions.checkArgument(StringUtils.isNoneBlank(genRequest.getArtifactId()), "artifactId不能为空");
        Preconditions.checkArgument(StringUtils.isNoneBlank(genRequest.getBasePackage()), "basePackage不能为空");
        //Preconditions.checkArgument(CollectionUtils.isNotEmpty(genRequest.getSubModules()), "subModules不能为空");

        if (genRequest.getDbModel() != null) {
            Preconditions.checkArgument(StringUtils.isNotBlank(genRequest.getDbModel().getDbType()), "dbType不能为空");
            Preconditions.checkArgument(StringUtils.isNotBlank(genRequest.getDbModel().getDbUrl()), "dbUrl不能为空");
            Preconditions.checkArgument(StringUtils.isNotBlank(genRequest.getDbModel().getDbUserName()), "dbUserName不能为空");
            Preconditions.checkArgument(StringUtils.isNotBlank(genRequest.getDbModel().getDbPassword()), "dbPassword不能为空");
            Preconditions.checkArgument(CollectionUtils.isNotEmpty(genRequest.getDbModel().getTables()), "tables不能为空");
        }
        return genRequest;
    }

    @Override
    public String genFileName(CodeGenDataModel dataModel) {
        if (!(dataModel instanceof GrailFrameworkDataModel)) {
            return getDefaultGenFileName();
        }
        return dataModel.getProject().getArtifactId().toLowerCase();
    }

}
