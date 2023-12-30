package com.itgrail.grail.codegen.service.dto;

//import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * @author xujin
 * created at 2019/5/25 18:31
 **/
@Data
@Accessors(chain = true)
//@ApiModel
public class GenCodeTemplateReqDTO implements Serializable {

    @NotEmpty(message = "templateName不能为空")
    private String templateName;

    @NotEmpty(message = "grailFrameworkVersion不能为空")
    private String grailFrameworkVersion;

    @NotEmpty(message = "javaVersion不能为空")
    private String javaVersion;
    @NotEmpty(message = "springBootVersion不能为空")
    private String springBootVersion;
    @NotEmpty(message = "springCloudVersion不能为空")
    private String springCloudVersion;

    @NotEmpty(message = "groupId不能为空")
    private String groupId;
    @NotEmpty(message = "artifactId不能为空")
    private String artifactId;

    private String description;
    @NotEmpty(message = "basePackage不能为空")
    private String basePackage;

    @NotEmpty(message = "subModules不能为空")
    List<@NotEmpty(message = "subModule不能为空") String> subModules;
}
