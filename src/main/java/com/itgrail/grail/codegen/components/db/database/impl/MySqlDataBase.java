package com.itgrail.grail.codegen.components.db.database.impl;

import com.itgrail.grail.codegen.components.db.database.DBProperties;
import com.itgrail.grail.codegen.components.db.common.JavaTypeResolver;
import com.itgrail.grail.codegen.components.db.database.AbstractDatabase;
import com.itgrail.grail.codegen.components.db.enums.DbTypeEnum;
import com.itgrail.grail.codegen.components.db.exceptions.DbException;
import com.itgrail.grail.codegen.components.db.model.Column;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.lang3.StringUtils;

import java.sql.*;
import java.util.List;

@Slf4j
public class MySqlDataBase extends AbstractDatabase {

    /**
     * MySQL查询表状态的执行SQL
     */
    private static final String TABLE_COMMENT_SQL = "show table status where NAME=?";

    private static final String TABLE_COLUMN_DESC = "desc `%s`";

    /**
     * 表备注字段名称
     */
    private static final String TABLE_COMMENT_COLUMN = "COMMENT";

    private String dbName;
    private DbTypeEnum dbTypeEnum;


    public MySqlDataBase(DBProperties dbProperties) {
        super(dbProperties);
        this.dbTypeEnum = DbTypeEnum.MySQL;
        init();
    }

    protected void init() {
        try {
            Class.forName(getDriverClassName());
            this.connection = DriverManager.getConnection(getDbUrl(), getDbUserName(), getDbPassword());
            this.dbName = connection.getCatalog();
        } catch (Exception ex) {
            DbUtils.closeQuietly(connection);
            throw new IllegalArgumentException("DB的用户名、密码、URL存在错误");
        }
        if (StringUtils.isEmpty(dbName)) {
            throw new IllegalArgumentException("必须在DB的URL中指定数据库名");
        }
    }

    @Override
    public String getDbUrl() {
        return dbProperties.getDbUrl();
    }

    @Override
    public String getDbName() {
        return this.dbName;
    }

    @Override
    public String getDbUserName() {
        return dbProperties.getDbUserName();
    }

    @Override
    public String getDbPassword() {
        return dbProperties.getDbPassword();
    }

    @Override
    public String getDriverClassName() {
        return dbTypeEnum.getDriverClassName();
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public String getTableComment(String tableName) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = getConnection().prepareStatement(TABLE_COMMENT_SQL);
            statement.setString(1, tableName);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString(TABLE_COMMENT_COLUMN);
            }
        } catch (SQLException ex) {
            log.error(String.format("查询数据库%s中表tableName=%s的comment信息失败", getDbName(), tableName), ex);
        } finally {
            DbUtils.closeQuietly(statement);
            DbUtils.closeQuietly(resultSet);
        }
        throw new DbException(String.format("查询数据库%s中表tableName=%s的comment信息失败", getDbName(), tableName));
    }

    @Override
    protected void postProcessColumns(String tableName, List<Column> columns) throws DbException {
        if (CollectionUtils.isEmpty(columns)) {
            return;
        }
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = getConnection().prepareStatement(String.format(TABLE_COLUMN_DESC, tableName));
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String field = resultSet.getString("Field");
                String type = resultSet.getString("Type");
                for (Column column : columns) {
                    if (column.getColumnName().equalsIgnoreCase(field)) {
                        column.setDefinedDataTypeName(type);
                    }
                }
            }
        } catch (SQLException ex) {
            String msg = String.format("查询数据库%s中表tableName=%s的desc信息失败", getDbName(), tableName);
            log.error(msg, ex);
            throw new DbException(msg);
        } finally {
            DbUtils.closeQuietly(statement);
            DbUtils.closeQuietly(resultSet);
        }
    }

    @Override
    protected void resolveColumnJavaType(List<Column> columns) {
        for (Column column : columns) {
            column.setJavaType(JavaTypeResolver.getJavaType(column.getDataType(), column.getDefinedDataTypeName()));
            column.setJavaFullType(JavaTypeResolver.getJavaFullType(column.getDataType(), column.getDefinedDataTypeName()));
        }
    }

    @Override
    public DbTypeEnum getDbType() {
        return dbTypeEnum;
    }
}