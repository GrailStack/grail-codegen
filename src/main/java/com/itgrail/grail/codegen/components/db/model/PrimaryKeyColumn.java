package com.itgrail.grail.codegen.components.db.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author xujin
 * created at 2019/6/3 20:45
 **/
@Getter
@Setter
@Accessors(chain = true)
public class PrimaryKeyColumn extends Column {

    public PrimaryKeyColumn() {
        setPrimaryKey(true);
    }

    private int keySeq = 1;
    private String pkName;
}
