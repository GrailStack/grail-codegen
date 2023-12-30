package com.itgrail.grail.codegen.template.custom;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author xujin
 * created at 2019/5/28 17:23
 **/
@Data
@Accessors(chain = true)
public class TemplateMetaData {
    private String templateName;
}
