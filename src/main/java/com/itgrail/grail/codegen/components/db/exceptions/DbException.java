package com.itgrail.grail.codegen.components.db.exceptions;

/**
 * @author xujin
 * created at 2019/6/3 16:58
 **/
public class DbException extends RuntimeException {

    public DbException() {
        super("DB操作失败");
    }

    public DbException(String msg) {
        super(msg);
    }

}
