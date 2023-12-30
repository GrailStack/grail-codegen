package com.itgrail.grail.codegen.core;

import com.alibaba.fastjson.JSONObject;
import com.itgrail.grail.codegen.core.plugin.ComponentPlugin;
import com.itgrail.grail.codegen.core.plugin.ComponentPluginCenter;
import com.itgrail.grail.codegen.utils.CommonUtil;
import com.itgrail.grail.codegen.utils.FileUtil;
import com.itgrail.grail.codegen.utils.ResourceUtil;
import com.itgrail.grail.codegen.utils.ZipUtil;
import com.itgrail.grail.codegen.template.custom.CustomizationCenter;
import com.itgrail.grail.codegen.template.custom.CustomizedTemplate;
import com.itgrail.grail.codegen.template.datamodel.CodeGenDataModel;
import com.itgrail.grail.codegen.template.datamodel.MavenModuleDataModel;
import com.itgrail.grail.codegen.template.datamodel.MavenProjectDataModel;
import com.itgrail.grail.codegen.template.engine.FreemarkerTemplateEngine;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author xujin
 * created at 2019/5/24 16:39
 **/
@Slf4j
@Component
public class TemplateProcessor {

    @Autowired
    private CustomizationCenter customizationCenter;

    @Autowired
    private ComponentPluginCenter pluginCenter;

    private static final ExecutorService threadPool = Executors.newFixedThreadPool(1);

    public byte[] process(CodeGenDataModel codeGenDataModel) {
        String templateName = codeGenDataModel.getTemplateName();
        CustomizedTemplate template = customizationCenter.getTemplate(templateName);
        if (template == null) {
            log.error("模板不存在，templateName={}", templateName);
            return null;
        }
        String outputPath = null;
        try {
            Resource templateDir = ResourceUtil.getDirResource(template.getTemplateLocatePath());
            if (templateDir == null) {
                log.error("模板不存在，templateName={}", templateName);
                return null;
            }

            outputPath = genTmpPathForDir(codeGenDataModel.getProject().getArtifactId().toLowerCase());
            handleFromTemplateRoot(templateDir, new File(outputPath), template, codeGenDataModel);

            return FileUtil.readFileToBytes(ZipUtil.zipToFile(outputPath));
        } catch (Exception ex) {
            log.error(String.format("ftl模板处理失败，templateName=%s, dataModel=%s", templateName, JSONObject.toJSONString(codeGenDataModel)), ex);
        } finally {
            asynClear(outputPath);
        }
        return null;
    }

    protected void handleFromTemplateRoot(Resource templateRoot, File toDir, CustomizedTemplate template, CodeGenDataModel codeGenDataModel) throws Exception {
        for (Resource resource : ResourceUtil.getDirResourceAllChildren(templateRoot)) {
            handle(resource, toDir, template, codeGenDataModel);
        }
    }

    protected void handle(Resource fromResource, File toDir, CustomizedTemplate template, CodeGenDataModel codeGenDataModel) throws IOException {
        if (ResourceUtil.isDirResource(fromResource)) {
            handleForDir(fromResource, toDir, template, codeGenDataModel);
        } else {
            handleForFile(fromResource, toDir, template, codeGenDataModel);
        }
    }

    protected void handleForFile(Resource file, File toDir, CustomizedTemplate template, CodeGenDataModel codeGenDataModel) throws IOException {
        Optional<ComponentPlugin> plugToHandleFile = pluginCenter.findPlugToHandleFile(file, codeGenDataModel);
        if (plugToHandleFile.isPresent()) {
            plugToHandleFile.get().handleFile(file, toDir, codeGenDataModel);
        } else if (isVoidFtlFileForPlaceHolder(file)) {
            //ignore
        } else if (isFtlFile(file)) {
            String result = new FreemarkerTemplateEngine().process(codeGenDataModel, ResourceUtil.getResourceUrlName(file));
            //忽略空文件
            if (StringUtils.isNotBlank(result)) {
                String resourceName = ResourceUtil.getResourceName(file);
                FileUtil.writeToFile(result, new File(toDir, resourceName.substring(0, resourceName.lastIndexOf(".ftl"))));
            }
        } else {
            FileUtil.copyResourceToFile(file, new File(toDir, ResourceUtil.getResourceName(file)));
        }
    }

    protected void handleForDir(Resource fromDir, File toDir, CustomizedTemplate template, CodeGenDataModel codeGenDataModel) throws IOException {
        String fromDirName = ResourceUtil.getResourceName(fromDir);
        if (isSubModelDir(fromDirName)) {
            if (handleAsSubModel(fromDirName, template, codeGenDataModel)) {
                toDir = new File(toDir, getSubModelRootDir(fromDirName, codeGenDataModel));
            } else {
                //不需要生成该子模块
                return;
            }
        } else if (isBasePackageDir(fromDirName)) {
            String basePackage = codeGenDataModel.getProject().getBasePackage();
            toDir = new File(toDir, basePackage.replaceAll("\\.", "/"));
        } else if (isSubBasePackageDir(fromDirName)) {
            String subModelBasePackage = getSubModelBasePackage(fromDirName, codeGenDataModel);
            if (StringUtils.isNotEmpty(subModelBasePackage)) {
                toDir = new File(toDir, subModelBasePackage.replaceAll("\\.", "/"));
            } else {
                toDir = new File(toDir, fromDirName);
            }
        } else {
            toDir = new File(toDir, fromDirName);
        }
        if (!toDir.exists()) {
            toDir.mkdirs();
        }
        for (Resource resource : ResourceUtil.getDirResourceAllChildren(fromDir)) {
            handle(resource, toDir, template, codeGenDataModel);
        }
    }

    protected String getSubModelRootDir(String dirName, CodeGenDataModel codeGenDataModel) {
        Map<String, MavenModuleDataModel> subModelMap = Optional.ofNullable(codeGenDataModel.getProject())
                .map(MavenProjectDataModel::getSubModules).orElse(new HashMap<>());
        for (String key : subModelMap.keySet()) {
            if (dirName.toLowerCase().endsWith(key)) {
                return subModelMap.get(key).getArtifactId().toLowerCase();
            }
        }
        return null;
    }

    protected String getSubModelBasePackage(String dirName, CodeGenDataModel codeGenDataModel) {
        String subModel = dirName.substring(dirName.lastIndexOf("-"));
        Map<String, MavenModuleDataModel> subModelMap = Optional.ofNullable(codeGenDataModel.getProject())
                .map(MavenProjectDataModel::getSubModules).orElse(new HashMap<>());
        for (String key : subModelMap.keySet()) {
            if (dirName.toLowerCase().endsWith(key)) {
                return subModelMap.get(key).getBasePackage();
            }
        }
        return null;
    }

    protected boolean isSubModelDir(String dirName) {
        return dirName.startsWith("-");
    }

    protected boolean handleAsSubModel(String dirName, CustomizedTemplate template, CodeGenDataModel codeGenDataModel) {
        if (template.hasSubModel(dirName)) {
            String subModelRootDir = getSubModelRootDir(dirName, codeGenDataModel);
            return StringUtils.isNotEmpty(subModelRootDir);
        }
        return false;
    }


    protected boolean isBasePackageDir(String dirName) {
        return dirName.toLowerCase().equalsIgnoreCase("basePackage");
    }

    protected boolean isSubBasePackageDir(String dirName) {
        return dirName.toLowerCase().startsWith("basePackage-".toLowerCase());
    }

    protected boolean isFtlFile(Resource file) throws IOException {
        return ResourceUtil.getResourceName(file).endsWith(".ftl");
    }

    protected boolean isVoidFtlFileForPlaceHolder(Resource file) throws IOException {
        return ResourceUtil.getResourceName(file).toLowerCase().equals("void.ftl");
    }

    protected static String genTmpPathForDir(String dir) {
        String tmpDir = System.getProperty("java.io.tmpdir");
        String path = tmpDir.replace("\\", "/") +
                "/" + "grail-codegen-" + CommonUtil.genUUID() +
                "/" + dir;
        FileUtil.buildOutputPath(path);
        return path;
    }

    protected void asynClear(String outputPath) {
        threadPool.submit(() -> clear(outputPath));
    }

    protected void clear(String outputPath) {
        if (outputPath == null) {
            return;
        }
        File file = new File(outputPath);
        if (file.getParentFile().getName().startsWith("grail-codegen-")) {
            FileUtil.removeDir(file.getParentFile());
        }
    }


}
