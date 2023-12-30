package com.itgrail.grail.codegen.service.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author xujin
 * created at 2019/6/6 18:00
 **/
@Data
public class DbTablesRespDTO implements Serializable {

    private String dbName;

    private List<String> tables;

}
