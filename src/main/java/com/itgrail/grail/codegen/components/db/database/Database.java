package com.itgrail.grail.codegen.components.db.database;

import com.itgrail.grail.codegen.components.db.enums.DbTypeEnum;
import com.itgrail.grail.codegen.components.db.model.Column;
import com.itgrail.grail.codegen.components.db.model.PrimaryKeyColumn;
import com.itgrail.grail.codegen.components.db.model.Table;

import java.io.Closeable;
import java.sql.Connection;
import java.util.List;

public interface Database extends Closeable {

    String getDbUrl();

    String getDbName();

    String getDbUserName();

    String getDbPassword();

    String getDriverClassName();

    Connection getConnection();

    DbTypeEnum getDbType();

    /**
     * 获取表名称列表
     *
     * @param tableNamePattern 获取表名时使用的表达式
     * @return 表名列表
     */
    List<String> getTableNames(String tableNamePattern);

    /**
     * 获取数据库所有表名称
     *
     * @return 所有表名称
     */
    List<String> getAllTableNames();

    /**
     * 查询表所有列
     *
     * @param tableName 表名
     * @return 所有列
     */
    List<Column> getColumns(String tableName);

    /**
     * 查询表主键列
     *
     * @param tableName 表名
     * @return 主键列
     */
    List<PrimaryKeyColumn> getPrimaryColumns(String tableName);


    /**
     * 查询数据库中所有表
     *
     * @return 数据表列表
     */
    List<Table> getAllTables();

    /**
     * 查询表
     *
     * @param tableNamePattern 表名称表达式过滤，如：sys_%，则仅仅查询出【sys_】开头的所有表
     * @return 数据表列表
     */
    List<Table> getTables(String tableNamePattern);

    /**
     * 查询表
     *
     * @param tableNames 表名称列表
     * @return 数据表列表
     */
    List<Table> getTables(List<String> tableNames);

    /**
     * 查询表
     *
     * @param tableName 表名
     * @return 表对象实例
     */
    Table getTable(String tableName);


    /**
     * 是否为主键列
     *
     * @param tableName  表名
     * @param columnName 列名
     * @return 是否为主键，true：主键，false：非主键
     */
    boolean isPrimaryKey(String tableName, String columnName);

    /**
     * 获取表备注信息
     *
     * @param tableName 表名
     * @return 表备注信息
     */
    String getTableComment(String tableName);

}