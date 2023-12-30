package com.itgrail.grail.codegen.custom.grailframework.metadata;

import lombok.Data;

/**
 * @author xujin
 * created at 2019/5/29 17:07
 **/
@Data
public class GrailVersionMetaData {
    private String grailFrameworkVersion;
    private String javaVersion;
    private String springBootVersion;
    private String springCloudVersion;
}
