package com.itgrail.grail.codegen.template.datamodel;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author xujin
 * created at 2019/5/24 13:35
 **/
@Data
@Accessors(chain = true)
public class SpringDataModel {

    private String springBootVersion;
    private String springCloudVersion;

}
