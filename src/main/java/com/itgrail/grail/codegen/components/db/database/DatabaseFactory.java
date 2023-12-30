package com.itgrail.grail.codegen.components.db.database;

import com.itgrail.grail.codegen.components.db.enums.DbTypeEnum;
import com.itgrail.grail.codegen.components.db.exceptions.DbException;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author xujin
 * created at 2019/6/4 18:25
 **/
@Slf4j
public class DatabaseFactory {

    private DatabaseFactory() {
    }

    public static Database create(DBProperties dbProperties) throws DbException {
        DbTypeEnum dbTypeEnum = DbTypeEnum.get(dbProperties.getDbType());
        if (dbTypeEnum == null) {
            throw new DbException(String.format("暂不支持该数据库类型, dbType=%s", dbProperties.getDbType()));
        }
        try {
            Constructor<? extends Database> constructor = dbTypeEnum.getDataBaseImplClass().getConstructor(DBProperties.class);
            return constructor.newInstance(dbProperties);
        } catch (InvocationTargetException ex) {
            log.error("创建database对象失败", ex);
            throw new DbException(ex.getCause().getMessage());
        } catch (Exception ex) {
            log.error("创建database对象失败", ex);
            throw new DbException(ex.getMessage());
        }
    }

}
