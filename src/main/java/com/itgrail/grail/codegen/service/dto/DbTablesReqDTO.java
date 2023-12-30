package com.itgrail.grail.codegen.service.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author xujin
 * created at 2019/6/6 17:50
 **/
@Data
@Accessors(chain = true)
public class DbTablesReqDTO implements Serializable {

    private String dbType;
    private String dbUrl;
    private String dbUserName;
    private String dbPassword;

}
