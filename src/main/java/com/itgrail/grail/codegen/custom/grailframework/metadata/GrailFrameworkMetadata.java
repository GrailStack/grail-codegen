package com.itgrail.grail.codegen.custom.grailframework.metadata;

import com.itgrail.grail.codegen.components.db.database.DbMetaData;
import com.itgrail.grail.codegen.template.custom.ModuleMetaData;
import com.itgrail.grail.codegen.template.custom.TemplateMetaData;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author xujin
 * created at 2019/5/28 18:39
 **/
@Data
@Accessors(chain = true)
public class GrailFrameworkMetadata extends TemplateMetaData {

    private List<GrailVersionMetaData> grailVersions;
    private List<ModuleMetaData> subModules;
    private List<String> dependencies;
    private List<DbMetaData> databases;
    private ModuleMetaData parentModule;
}
