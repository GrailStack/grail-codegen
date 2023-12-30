package com.itgrail.grail.codegen.components.db.database;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author xujin
 * created at 2019/5/31 14:24
 **/
@Data
@Accessors(chain = true)
public class DBProperties {

    private String dbType;
    private String dbUrl;
    private String dbUserName;
    private String dbPassword;

}
