package com.itgrail.grail.codegen.template.engine;

import freemarker.cache.TemplateLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * Desc:
 *
 * @author xujin
 * @date 2019/6/14 11:08
 **/
public class ResourceTemplateLoader implements TemplateLoader {

    @Override
    public Object findTemplateSource(String name) throws IOException {
        return new PathMatchingResourcePatternResolver().getResource(name);
    }

    @Override
    public long getLastModified(Object templateSource) {
        return 0;
    }

    @Override
    public Reader getReader(Object templateSource, String encoding) throws IOException {
        Resource resource = (Resource) templateSource;
        return new InputStreamReader(resource.getInputStream(), "UTF-8");
    }

    @Override
    public void closeTemplateSource(Object templateSource) throws IOException {

    }

}
