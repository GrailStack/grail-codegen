package com.itgrail.grail.codegen.components.db.common;

/**
 * @author xujin
 * created at 2019/6/4 16:04
 **/
public class DbModelConverter {

    public static String columnNameToField(String columnName) {
        return CamelCaseFormat.getCamelCaseString(columnName, false);
    }

    public static String tableNameToDoName(String tableName) {
        String tableNameTmp = tableName;
        if (tableNameTmp.startsWith("t_")) {
            tableNameTmp = tableNameTmp.replaceFirst("t_", "");
        }
        tableNameTmp = CamelCaseFormat.getCamelCaseString(tableNameTmp, true);
        return tableNameTmp + "DO";
    }

    public static String tableNameToDaoName(String tableName) {
        String tableNameTmp = tableName;
        if (tableName.startsWith("t_")) {
            tableNameTmp = tableName.replaceFirst("t_", "");
        }
        tableNameTmp = CamelCaseFormat.getCamelCaseString(tableNameTmp, true);
        return tableNameTmp + "Mapper";
    }

}
