package com.itgrail.grail.codegen.components.db;

import lombok.Data;

/**
 * @author xujin
 * created at 2019/6/4 21:43
 **/
@Data
public class CurdDataModel {

    private String name;

    private String controllerName = name+"Controller";

    private String serviceName = name+"Service";
    private String serviceImplName = name+"serviceImpl";

    private String addCmoName = "Add"+name+"Cmd";
    private String pageCmoName = "Page"+name+"Cmd";
    private String updateCmoName = "Update"+name+"Cmd";

    private String coName = name+"CO";
}
