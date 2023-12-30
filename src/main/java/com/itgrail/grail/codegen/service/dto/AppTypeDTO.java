package com.itgrail.grail.codegen.service.dto;


import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class AppTypeDTO implements Serializable {

    /**
     * app应用类型名称
     */
    private String appTypeName;

    /***
     * app应用类型对应的Value
     */
    private String appTypeValue;

    /**
     * 应用描述
     */
    private String desc;

}
