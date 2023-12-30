package com.itgrail.grail.codegen.components.db.model;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author xujin
 */

@Data
@Accessors(chain = true)
public class Table {

    private String tableName;

    /**
     * 数据对象名称
     */
    private String doName;

    /**
     * 数据访问对象名称
     */
    private String daoName;

    /**
     * 表备注信息
     */
    private String comment;

    /**
     * 数据列列表
     */
    private List<Column> columns;

    /**
     * 主键列表
     */
    private List<PrimaryKeyColumn> primaryKeys;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this, SerializerFeature.WriteMapNullValue, SerializerFeature.PrettyFormat);
    }

}