package com.itgrail.grail.codegen.core.plugin;

import com.itgrail.grail.codegen.template.datamodel.CodeGenDataModel;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;

/**
 * @author xujin
 * created at 2019/6/4 21:29
 **/
public interface ComponentPlugin<T> {

    public boolean canHandleDir(Resource dir, CodeGenDataModel model);

    public void handleDir(Resource dir, File toDir, CodeGenDataModel model);

    public boolean canHandleFile(Resource file, CodeGenDataModel model);

    public void handleFile(Resource file, File toDir, CodeGenDataModel model) throws IOException;

}
