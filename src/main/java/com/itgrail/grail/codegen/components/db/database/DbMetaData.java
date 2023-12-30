package com.itgrail.grail.codegen.components.db.database;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author xujin
 * created at 2019/6/6 9:40
 **/
@Data
@Accessors(chain = true)
public class DbMetaData {
    private String dbType;
}
