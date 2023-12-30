package com.itgrail.grail.codegen.components.db.database;

import com.google.common.collect.Lists;
import com.itgrail.grail.codegen.components.db.common.DbModelConverter;
import com.itgrail.grail.codegen.components.db.enums.TableMetaDataEnum;
import com.itgrail.grail.codegen.components.db.exceptions.DbException;
import com.itgrail.grail.codegen.components.db.model.Column;
import com.itgrail.grail.codegen.components.db.model.PrimaryKeyColumn;
import com.itgrail.grail.codegen.components.db.model.Table;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.lang3.StringUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public abstract class AbstractDatabase implements Database {

    protected Connection connection;

    protected DBProperties dbProperties;

    public AbstractDatabase(DBProperties dbProperties) {
        this.dbProperties = dbProperties;
    }

    @Override
    public List<String> getAllTableNames() {
        return getTableNames("%");
    }

    @Override
    public List<String> getTableNames(String tableNamePattern) throws DbException {
        tableNamePattern = StringUtils.isBlank(tableNamePattern) ? "%" : tableNamePattern;
        try {
            ResultSet resultSet = connection.getMetaData()
                    .getTables(getDbName(), null, tableNamePattern, new String[]{"TABLE"});
            List<String> tables = new ArrayList<String>();
            while (resultSet.next()) {
                String tableName = resultSet.getString(TableMetaDataEnum.TABLE_NAME.getValue());
                tables.add(tableName);
            }
            return tables;
        } catch (Exception ex) {
            String msg = String.format("查询数据库%s中表名称失败，tableNamePattern=%s", getDbName(), tableNamePattern);
            log.error(msg, ex);
            throw new DbException(msg);
        }
    }

    @Override
    public List<Table> getAllTables() throws DbException {
        return getTables("%");
    }

    @Override
    public List<Table> getTables(String tableNamePattern) throws DbException {
        try {
            List<String> tableNames = getTableNames(tableNamePattern);
            if (CollectionUtils.isEmpty(tableNames)) {
                return Lists.newArrayList();
            }
            List<Table> tables = new ArrayList<Table>();
            for (String tableName : tableNames) {
                tables.add(getTable(tableName));
            }
            return tables;
        } catch (Exception ex) {
            String msg = String.format("查询数据库%s中表失败，tableNamePattern=%s", getDbName(), tableNamePattern);
            log.error(msg, ex);
            throw new DbException(msg);
        }
    }

    public List<Table> getTables(List<String> tableNames) throws DbException {
        if (CollectionUtils.isEmpty(tableNames)) {
            return Lists.newArrayList();
        }
        List<Table> tables = Lists.newArrayList();
        for (String tableName : tableNames) {
            tables.add(getTable(tableName));
        }
        return tables;
    }

    @Override
    public Table getTable(String tableName) {
        try {
            ResultSet resultSet = getConnection().getMetaData().getTables(getDbName(), null, tableName, new String[]{"TABLE"});
            if (resultSet.next()) {
                Table table = new Table();
                table.setTableName(tableName);
                table.setDoName(DbModelConverter.tableNameToDoName(tableName));
                table.setDaoName(DbModelConverter.tableNameToDaoName(tableName));
                table.setComment(getTableComment(tableName));
                table.setColumns(getColumns(tableName));
                table.setPrimaryKeys(retrievePrimaryKeyColumns(table.getColumns()));
                table.setComment(getTableComment(tableName));
                return table;
            } else {
                throw new DbException(String.format("数据库%s中找不到表%s", getDbName(), tableName));
            }
        } catch (SQLException ex) {
            String msg = String.format("查询数据库%s中表%s信息失败", getDbName(), tableName);
            log.error(msg, ex);
            throw new DbException(msg);
        }
    }

    protected List<PrimaryKeyColumn> retrievePrimaryKeyColumns(List<Column> columns) {
        return columns.stream()
                .filter(c -> c instanceof PrimaryKeyColumn)
                .map(c -> (PrimaryKeyColumn) c)
                .collect(Collectors.toList());
    }

    @Override
    public List<Column> getColumns(String tableName) {
        List<Column> columns = new ArrayList<Column>();
        try {
            columns.addAll(getPrimaryColumns(tableName));
            ResultSet resultSet = getConnection().getMetaData().getColumns(getDbName(), null, tableName, "%");
            while (resultSet.next()) {
                Column column = new Column();
                fillColumnInfo(column, resultSet);
                if (!columns.contains(column)) {
                    columns.add(column);
                }
            }
            postProcessColumns(tableName, columns);
            resolveColumnJavaType(columns);
            return columns;
        } catch (SQLException ex) {
            String msg = String.format("查询数据库%s中表%s的列信息失败", getDbName(), tableName);
            log.error(msg, ex);
            throw new DbException(msg);
        }
    }

    /**
     * 由具体数据库实现进行特殊处理
     *
     * @param columns
     */
    protected void postProcessColumns(String tableName, List<Column> columns) {
    }

    /**
     * 解析字段对应的java类型
     *
     * @param columns
     */
    protected abstract void resolveColumnJavaType(List<Column> columns);

    /**
     * 获取表内的主键列表
     *
     * @param tableName 表名
     * @return 主键列表
     */
    public List<PrimaryKeyColumn> getPrimaryColumns(String tableName) {
        try {
            ResultSet resultSet = connection.getMetaData().getPrimaryKeys(getDbName(), null, tableName);
            List<PrimaryKeyColumn> pkColumns = new ArrayList<>();
            while (resultSet.next()) {
                PrimaryKeyColumn pkColumn = new PrimaryKeyColumn();
                pkColumn.setColumnName(resultSet.getString(TableMetaDataEnum.COLUMN_NAME.getValue()));
                pkColumn.setKeySeq(resultSet.getInt(TableMetaDataEnum.PK_KEY_SEQ.getValue()));
                pkColumn.setPkName(resultSet.getString(TableMetaDataEnum.PK_NAME.getValue()));
                pkColumns.add(pkColumn);
                ResultSet columnResultSet = connection.getMetaData().getColumns(getDbName(), null, tableName, pkColumn.getColumnName());
                if (columnResultSet.next()) {
                    fillColumnInfo(pkColumn, columnResultSet);
                }
            }
            Collections.sort(pkColumns, (pk1, pk2) -> Integer.compare(pk1.getKeySeq(), pk2.getKeySeq()));
            return pkColumns;
        } catch (SQLException ex) {
            String msg = String.format("查询数据库%s中表%s的主键列信息失败", getDbName(), tableName);
            log.error(msg, ex);
            throw new DbException(msg);
        }
    }

    protected void fillColumnInfo(Column column, ResultSet columnResultSet) throws SQLException {
        column.setTableName(columnResultSet.getString(TableMetaDataEnum.TABLE_NAME.getValue()));
        column.setColumnName(columnResultSet.getString(TableMetaDataEnum.COLUMN_NAME.getValue()));
        column.setDataType(columnResultSet.getInt(TableMetaDataEnum.DATA_TYPE.getValue()));
        column.setDataTypeName(columnResultSet.getString(TableMetaDataEnum.TYPE_NAME.getValue()));
        column.setComment(columnResultSet.getString(TableMetaDataEnum.REMARKS.getValue()));
        column.setDecimalDigits(columnResultSet.getInt(TableMetaDataEnum.DECIMAL_DIGITS.getValue()));
        column.setDefaultValue(columnResultSet.getString(TableMetaDataEnum.COLUMN_DEF.getValue()));
        column.setColumnSize(columnResultSet.getInt(TableMetaDataEnum.COLUMN_SIZE.getValue()));
        column.setAutoincrement(isTrue(columnResultSet.getString(TableMetaDataEnum.IS_AUTOINCREMENT.getValue())));
        column.setFieldName(DbModelConverter.columnNameToField(column.getColumnName()));
    }

    protected boolean isTrue(String str) {
        if (StringUtils.isEmpty(str)) {
            return false;
        }
        return "yes".equalsIgnoreCase(str) || "true".equalsIgnoreCase(str);
    }

    @Override
    public boolean isPrimaryKey(String tableName, String columnName) throws DbException {
        List<Column> columns = getColumns(tableName);
        for (Column column : columns) {
            if (column.getColumnName().equals(columnName)) {
                return column.isPrimaryKey();
            }
        }
        throw new DbException(String.format("表%s中不存在列%s", tableName, columnName));
    }

    @Override
    public void close() {
        DbUtils.closeQuietly(connection);
    }

}
