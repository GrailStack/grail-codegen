package com.itgrail.grail.codegen.components.db.enums;

import lombok.Getter;

@Getter
public enum TableMetaDataEnum {
    /**
     * 表类别(可为null)
     */
    TABLE_CAT("TABLE_CAT"),
    /**
     * 表模式（可能为空）,在oracle中获取的是命名空间
     */
    TABLE_SCHEMA("TABLE_SCHEM"),
    /**
     * 表类型 典型的类型是 "TABLE"、"VIEW"、"SYSTEM TABLE"、"GLOBAL TEMPORARY"、"LOCAL TEMPORARY"、"ALIAS" 和 "SYNONYM"
     */
    TABLE_TYPE("TABLE_TYPE"),
    /**
     * 表名
     */
    TABLE_NAME("TABLE_NAME"),
    /**
     * 列名
     */
    COLUMN_NAME("COLUMN_NAME"),
    /**
     * 外键列表
     */
    FK_COLUMN_NAME("FKCOLUMN_NAME"),
    /**
     * 列大小
     */
    COLUMN_SIZE("COLUMN_SIZE"),
    /**
     * 是否为空
     */
    NULLABLE("NULLABLE"),
    /**
     * 列默认值
     */
    COLUMN_DEF("COLUMN_DEF"),
    /**
     * 列是否自增
     */
    IS_AUTOINCREMENT("IS_AUTOINCREMENT"),
    /**
     * 列数据类型
     */
    DATA_TYPE("DATA_TYPE"),

    /**
     * 列数据类型name
     */
    TYPE_NAME("TYPE_NAME"),

    /**
     * 列备注信息
     */
    REMARKS("REMARKS"),

    /**
     * 小数位数。对于DECIMAL_DIGITS不适用的数据类型，将返回Null。
     */
    DECIMAL_DIGITS("DECIMAL_DIGITS"),

    /**
     * 表中列的索引（从1开始）
     */
    ORDINAL_POSITION("ORDINAL_POSITION"),

    PK_NAME("PK_NAME"),

    PK_KEY_SEQ("KEY_SEQ");

    private String value;

    TableMetaDataEnum(String value) {
        this.value = value;
    }
}
