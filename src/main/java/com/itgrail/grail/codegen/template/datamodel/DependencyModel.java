package com.itgrail.grail.codegen.template.datamodel;

import lombok.Data;

import java.io.Serializable;

@Data
public class DependencyModel implements Serializable {
    private String groupId;
    private String artifactId;
    private String version;
    private String desc;
    private Boolean optional;
    private String classify;
    private String scope;
    //用于文本显示
    private String display;

}
