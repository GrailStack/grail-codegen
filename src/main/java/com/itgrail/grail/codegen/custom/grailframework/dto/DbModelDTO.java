package com.itgrail.grail.codegen.custom.grailframework.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * Desc:
 *
 * @author xujin
 * @date 2019/6/15 14:47
 **/
@Data
@Accessors(chain = true)
public class DbModelDTO implements Serializable {
    private String dbType;
    private String dbUrl;
    private String dbUserName;
    private String dbPassword;
    private List<String> tables;
}
