package com.itgrail.grail.codegen.custom.grailframework.dto;

import com.itgrail.grail.codegen.template.custom.TemplateGenRequest;
import lombok.Data;
import lombok.experimental.Accessors;
import com.itgrail.grail.codegen.template.datamodel.ModuleDependencyModel;

import java.util.List;

/**
 * @author xujin
 * created at 2019/5/29 16:05
 **/
@Data
@Accessors(chain = true)
public class GrailFrameworkGenRequest extends TemplateGenRequest {

    private String javaVersion;
    private String grailFrameworkVersion;

    private String groupId;
    private String artifactId;
    private String description;

    private String basePackage;

    private List<SubModuleDTO> subModules;

    private DbModelDTO dbModel;

    private DomainDTO domain;

    /**
     * 是否需要连接数据库，不需要连接数据库,就不添加Grail  MyBatis
     */
    private Boolean dbConfigure;

    /**
     * 模块依赖
     */
    private List<ModuleDependencyModel> moduleDependencyModels;

}
