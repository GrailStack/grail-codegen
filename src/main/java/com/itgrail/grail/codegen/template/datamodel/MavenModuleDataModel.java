package com.itgrail.grail.codegen.template.datamodel;

import com.itgrail.grail.codegen.template.custom.CustomizedTemplate;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author xujin
 * created at 2019/5/24 11:41
 **/
@Data
@Accessors(chain = true)
public class MavenModuleDataModel {
    private String groupId;
    private String artifactId;
    private String basePackage;
    private String packaging;

    /**
     * @see CustomizedTemplate#getSubModules()
     */
    private String moduleName;
}
