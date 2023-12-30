package com.itgrail.grail.codegen.template.custom;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author xujin
 * created at 2019/5/28 17:29
 **/
@Data
@Accessors(chain = true)
public class TemplateGenRequest {
    private String templateName;
}
