package com.itgrail.grail.codegen.utils;

import com.google.common.collect.Lists;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;
import java.util.List;

/**
 * Desc:
 *
 * @author xujin
 * @date 2019/6/14 10:27
 **/
public class ResourceUtil {

    public static Resource getDirResource(String antStylePath) throws IOException {
        if (!antStylePath.endsWith("/")) {
            antStylePath = antStylePath + "/";
        }
        Resource[] resources = new PathMatchingResourcePatternResolver().getResources(antStylePath);
        if (resources.length < 1) {
            return null;
        }
        return resources[0];
    }

    public static boolean isDirResource(Resource resource) throws IOException {
        return resource.getURL().toString().endsWith("/");
    }

    public static String getResourceName(Resource resource) throws IOException {
        if (isDirResource(resource)) {
            String urlStr = resource.getURL().toString();
            String tmp = urlStr.substring(0, urlStr.length() - 1);
            return tmp.substring(tmp.lastIndexOf("/") + 1);
        } else {
            return resource.getFilename();
        }
    }

    public static String getResourceUrlName(Resource resource) throws IOException {
        return resource.getURL().toString();
    }

    public static List<Resource> getDirResourceAllChildren(Resource dirResource) throws IOException {
        List<Resource> resources = Lists.newArrayList();
        if (!isDirResource(dirResource)) {
            return resources;
        }
        resources.addAll(getDirResourceDirChildren(dirResource));
        resources.addAll((getDirResourceFileChildren(dirResource)));
        return resources;
    }

    public static List<Resource> getDirResourceDirChildren(Resource dirResource) throws IOException {
        if (!isDirResource(dirResource)) {
            return Lists.newArrayList();
        }
        Resource[] resources = new PathMatchingResourcePatternResolver().getResources(dirResource.getURL().toString() + "*/");
        return Lists.newArrayList(resources);
    }

    public static List<Resource> getDirResourceFileChildren(Resource dirResource) throws IOException {
        if (!isDirResource(dirResource)) {
            return Lists.newArrayList();
        }
        Resource[] resources = new PathMatchingResourcePatternResolver().getResources(dirResource.getURL().toString() + "*");
        return Lists.newArrayList(resources);
    }

}
