package com.itgrail.grail.codegen.template.engine;

import com.alibaba.fastjson.JSONObject;
import com.google.common.io.CharStreams;
import com.itgrail.grail.codegen.utils.CommonUtil;
import freemarker.template.Configuration;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.Writer;

/**
 * @author xujin
 * created at 2019/5/24 14:45
 **/
@Slf4j
public class FreemarkerTemplateEngine {
    private Configuration configuration = new Configuration(Configuration.VERSION_2_3_28);

    public FreemarkerTemplateEngine(Class resourceLoaderClass, String basePackagePath) {
        configuration.setDefaultEncoding("UTF-8");
        configuration.setClassForTemplateLoading(resourceLoaderClass, basePackagePath);
    }

    public FreemarkerTemplateEngine(File dir) {
        configuration.setDefaultEncoding("UTF-8");
        try {
            configuration.setDirectoryForTemplateLoading(dir);
        } catch (Exception ex) {
        }
    }

    public FreemarkerTemplateEngine() {
        configuration.setDefaultEncoding("UTF-8");
        try {
            configuration.setTemplateLoader(new ResourceTemplateLoader());
            configuration.setLocalizedLookup(false);
        } catch (Exception ex) {
        }
    }

    public String process(Object dataModel, String templateName) {
        StringBuilder stringBuilder = new StringBuilder();
        Writer writer = CharStreams.asWriter(stringBuilder);
        try {
            process(dataModel, templateName, writer);
            writer.flush();
            return stringBuilder.toString();
        } catch (Exception ex) {
            log.error(String.format("ftl模板处理失败，templateName=%s, dataModel=%s", templateName, JSONObject.toJSONString(dataModel)), ex);
        } finally {
            CommonUtil.closeClosable(writer);
        }
        return null;
    }

    public void process(Object dataModel, String templateName, Writer out) throws Exception {
        configuration.getTemplate(templateName).process(dataModel, out);
    }

}
