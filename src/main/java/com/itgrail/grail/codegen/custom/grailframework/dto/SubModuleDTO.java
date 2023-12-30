package com.itgrail.grail.codegen.custom.grailframework.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * Desc:
 *
 * @author xujin
 * @date 2019/6/13 11:33
 **/
@Data
@Accessors(chain = true)
public class SubModuleDTO implements Serializable {
    private String subModuleName;
    private String packaging;
}
