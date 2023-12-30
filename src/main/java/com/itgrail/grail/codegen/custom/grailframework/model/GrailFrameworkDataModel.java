package com.itgrail.grail.codegen.custom.grailframework.model;

import com.itgrail.grail.codegen.components.db.DbDataModel;
import com.itgrail.grail.codegen.template.datamodel.CodeGenDataModel;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author xujin
 * created at 2019/5/27 15:55
 **/
@Data
@Accessors(chain = true)
public class GrailFrameworkDataModel extends CodeGenDataModel {
    private String grailFrameworkVersion;

    private DbDataModel db;

    private DomainDataModel domain;
}
