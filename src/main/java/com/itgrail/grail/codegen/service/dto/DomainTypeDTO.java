package com.itgrail.grail.codegen.service.dto;


import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author lege
 * created at 2022/1/21 00:00
 **/
@Data
@Accessors(chain = true)
public class DomainTypeDTO implements Serializable {

    /***
     * 域名称
     */
    private String name;

    /**
     * 域code
     */
    private String code;

    /**
     * 应用描述
     */
    private String domainDesc;

    /**
     * 父域code
     */
    private String parentCode;

    /**
     * 子域集合
     */
    private List<DomainTypeDTO> subDomains;

}
