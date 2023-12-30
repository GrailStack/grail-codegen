package com.itgrail.grail.codegen.template.custom;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author xujin
 * created at 2019/5/29 17:48
 **/
@Data
@Accessors(chain = true)
public class GenResult {
    private byte[] fileBytes;
    private String fileName;
}
