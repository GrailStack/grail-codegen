package com.itgrail.grail.codegen.template.datamodel;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ModuleDependencyModel implements Serializable {

    /**
     * 模块唯一的key
     */
    private String modulekey;

    /**
     *  依赖
     */
    private List<DependencyModel> dependencyModels;


}
