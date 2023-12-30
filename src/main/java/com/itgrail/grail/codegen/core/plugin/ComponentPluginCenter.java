package com.itgrail.grail.codegen.core.plugin;

import com.itgrail.grail.codegen.template.datamodel.CodeGenDataModel;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * @author xujin
 * created at 2019/6/5 10:30
 **/
@Component
public class ComponentPluginCenter {

    @Autowired
    private List<ComponentPlugin> plugins;

    public Optional<ComponentPlugin> findPlugToHandleFile(Resource file, CodeGenDataModel model) {
        if (CollectionUtils.isEmpty(plugins)) {
            return Optional.empty();
        }
        for (ComponentPlugin plugin : plugins) {
            if (plugin.canHandleFile(file, model)) {
                return Optional.of(plugin);
            }
        }
        return Optional.empty();
    }

    public Optional<ComponentPlugin> findPlugToHandleDir(Resource dir, CodeGenDataModel model) {
        if (CollectionUtils.isEmpty(plugins)) {
            return Optional.empty();
        }
        for (ComponentPlugin plugin : plugins) {
            if (plugin.canHandleDir(dir, model)) {
                return Optional.of(plugin);
            }
        }
        return Optional.empty();
    }

}
