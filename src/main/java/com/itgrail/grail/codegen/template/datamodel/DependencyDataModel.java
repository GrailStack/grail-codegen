package com.itgrail.grail.codegen.template.datamodel;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author xujin
 * created at 2019/5/24 13:47
 **/
@Data
@Accessors(chain = true)
public class DependencyDataModel {
    private String groupId;
    private String artifactId;
    private String version;
    /**
     * 唯一id
     */
    private String uid;
}
