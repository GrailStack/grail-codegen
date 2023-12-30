package com.itgrail.grail.codegen.template.custom;

import com.itgrail.grail.codegen.template.datamodel.DependencyModel;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * Desc:
 *
 * @author xujin
 * @date 2019/6/13 11:16
 **/
@Data
@Accessors(chain = true)
public class ModuleMetaData implements Serializable {
    private String subModuleName;
    private List<String> packagingTypes;
    private List<DependencyModel> dependencys;
}
