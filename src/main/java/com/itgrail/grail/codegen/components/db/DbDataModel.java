package com.itgrail.grail.codegen.components.db;

import com.itgrail.grail.codegen.components.db.model.Table;
import lombok.Data;

import java.util.List;

/**
 * @author xujin
 * created at 2019/6/4 21:43
 **/
@Data
public class DbDataModel {

    private List<Table> tables;

    private String dbName;
    private String dbUrl;
    private String dbUserName;
    private String dbPassword;
    private String dbDriver;

    //用于迭代
    private Table table;

}
