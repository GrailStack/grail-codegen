package com.itgrail.grail.codegen.template.datamodel;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author xujin
 * created at 2019/5/24 13:34
 **/
@Data
@Accessors(chain = true)
public class CodeGenDataModel {
    private String templateName;
    private String javaVersion;
    private MavenProjectDataModel project;
}
