package com.itgrail.grail.codegen.custom.grailframework.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * Desc:
 *
 * @author xujin
 * @date 2019/12/3 20:18
 **/
@Data
@Accessors(chain = true)
public class DomainDataModel implements Serializable {

    /**
     * 当前应用所属域的唯一code
     */
    private String code;

    /**
     * 父域的唯一code
     */
    private String parentCode;

    /**
     * 当前域的名称
     */
    private String name;

    /**
     * 当前域的描述
     */
    private String desc;

}
