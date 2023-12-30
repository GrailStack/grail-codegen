package com.itgrail.grail.codegen.service.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author xujin
 * created at 2019/5/26 23:38
 **/
@Data
@Accessors(chain = true)
public class GenCodeTemplateRespDTO implements Serializable {
    private byte[] fileBytes;
    private String fileName;
}
