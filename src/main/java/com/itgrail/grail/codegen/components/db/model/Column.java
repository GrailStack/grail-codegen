package com.itgrail.grail.codegen.components.db.model;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
public class Column implements Serializable {

    public Column() {
        setPrimaryKey(false);
    }

    /**
     * 列名
     */
    protected String columnName;

    /**
     * 该列归属的table
     */
    protected String tableName;

    /**
     * 列长度
     */
    protected int columnSize;

    /**
     * 数据类型
     *
     * @see java.sql.Types
     */
    protected int dataType;

    /**
     * 数据类型名称
     */
    protected String dataTypeName;

    /**
     * 显示定义的数据类型名称
     */
    protected String definedDataTypeName;

    /**
     * 小数点位数
     */
    protected int decimalDigits;

    /**
     * 是否为空
     */
    protected boolean nullable;

    /**
     * 是否自增
     */
    protected boolean autoincrement;

    /**
     * 默认值
     */
    protected String defaultValue;

    /**
     * 备注信息
     */
    protected String comment;

    /**
     * 该列对应的java字段名称
     */
    protected String fieldName;

    /**
     * java数据类型(short name)
     */
    protected String javaType;

    /**
     * java数据类型(full qualified name)
     */
    protected String javaFullType;

    /**
     * 是否为主键列
     */
    protected boolean primaryKey;

    @Override
    public int hashCode() {
        return Objects.hash(this.getTableName(), this.getColumnName());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Column)) {
            return false;
        }
        Column otherColumn = (Column) obj;
        return this.getTableName().equals(otherColumn.getTableName())
                && this.getColumnName().equals(otherColumn.getColumnName());
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this, SerializerFeature.WriteMapNullValue, SerializerFeature.PrettyFormat);
    }

}