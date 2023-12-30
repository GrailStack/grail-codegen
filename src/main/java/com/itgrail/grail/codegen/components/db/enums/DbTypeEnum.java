package com.itgrail.grail.codegen.components.db.enums;

import com.itgrail.grail.codegen.components.db.database.impl.MySqlDataBase;
import com.itgrail.grail.codegen.components.db.database.Database;
import lombok.Getter;

/**
 * @author xujin
 * created at 2019/6/4 18:02
 **/
@Getter
public enum DbTypeEnum {

    MySQL("com.mysql.jdbc.Driver", MySqlDataBase.class);

    private String driverClassName;
    private Class<? extends Database> dataBaseImplClass;

    DbTypeEnum(String driverClassName, Class<? extends Database> dataBaseImplClass) {
        this.driverClassName = driverClassName;
        this.dataBaseImplClass = dataBaseImplClass;
    }

    public static DbTypeEnum get(String dbType) {
        for (DbTypeEnum dbTypeEnum : DbTypeEnum.values()) {
            if (dbTypeEnum.name().equalsIgnoreCase(dbType)) {
                return dbTypeEnum;
            }
        }
        return null;
    }

}
