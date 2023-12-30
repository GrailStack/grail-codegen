package com.itgrail.grail.codegen.service.dto;

import com.itgrail.grail.codegen.template.custom.TemplateMetaData;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author xujin
 * created at 2019/5/22 18:29
 **/
@Data
@Accessors(chain = true)
public class GetMetaDataRespDTO implements Serializable {

    private TemplateMetaData metaData;

}
