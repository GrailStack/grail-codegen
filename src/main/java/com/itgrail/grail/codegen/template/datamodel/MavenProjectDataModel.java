package com.itgrail.grail.codegen.template.datamodel;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author xujin
 * created at 2019/5/24 14:08
 **/
@Data
@Accessors(chain = true)
public class MavenProjectDataModel {
    private String groupId;

    private String artifactId;

    private String basePackage;

    private String description;

    private Boolean dbConfigure=false;

    /**
     * key:
     *
     * @see MavenModuleDataModel#getModuleName()
     */
    private Map<String, MavenModuleDataModel> subModules;


    /**
     * 依赖
     */
    private List<ModuleDependencyModel> dependencies;

    /**
     * 用于判断某个 module 中是否包含了特定 dependency, 用于针对不同 dependency 生成对应的代码
     * @param moduleName
     * @param artifactId
     * @return
     */
    public boolean hasDependencyForModule(String moduleName, String artifactId) {
        Optional<ModuleDependencyModel> module = dependencies.stream()
                .filter(mod -> moduleName.equals(mod.getModulekey()))
                .findFirst();
        if (!module.isPresent()) {
            return false;
        }
        return module.get().getDependencyModels().stream().anyMatch(dep -> artifactId.equals(dep.getArtifactId()));
    }
}
