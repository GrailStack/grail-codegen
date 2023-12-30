package com.itgrail.grail.codegen.components.openfeign;

import com.itgrail.grail.codegen.core.plugin.ComponentPlugin;
import com.itgrail.grail.codegen.utils.FileUtil;
import com.itgrail.grail.codegen.utils.ResourceUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import com.itgrail.grail.codegen.template.datamodel.CodeGenDataModel;
import com.itgrail.grail.codegen.template.engine.FreemarkerTemplateEngine;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * 用于判断是否生成 openfeign 逻辑
 * @author xujin
 */
@Component
@Slf4j
public class FeignComponentPlugin implements ComponentPlugin {

    private static final Pattern[] feignFiles = {
            Pattern.compile("/basePackage/.*config/FeignConfig.java.ftl"),
            Pattern.compile("/basePackage/.*feign/.+"),
            Pattern.compile("/basePackage/.*service/PeopleService.java.ftl"),
            Pattern.compile("/basePackage/.*service/impl/PeopleServiceImpl.java.ftl"),
    };

    @Override
    public boolean canHandleDir(Resource dir, CodeGenDataModel model) {
        return false;
    }

    @Override
    public void handleDir(Resource dir, File toDir, CodeGenDataModel model) {

    }

    @Override
    public boolean canHandleFile(Resource file, CodeGenDataModel model) {
        if (Optional.ofNullable(model.getProject())
                .map(proj -> proj.hasDependencyForModule("service", "grail-boot-starter-openfeign")
                        || proj.hasDependencyForModule("parent", "grail-boot-starter-openfeign"))
                .orElse(false)) {
            return false;
        }

        return isFeignFile(file);
    }

    private boolean isFeignFile(Resource file) {
        try {
            String resourceName = ResourceUtil.getResourceUrlName(file);
            for (Pattern ptn : feignFiles) {
                if (ptn.matcher(resourceName).find()) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void handleFile(Resource file, File toDir, CodeGenDataModel model) throws IOException {
        String result = new FreemarkerTemplateEngine().process(model, ResourceUtil.getResourceUrlName(file));
        String resourceName = ResourceUtil.getResourceName(file);

        if (StringUtils.isNotBlank(result)) {
            FileUtil.writeToFile(result, new File(toDir, resourceName.substring(0, resourceName.lastIndexOf(".ftl"))));
        }
    }

}
