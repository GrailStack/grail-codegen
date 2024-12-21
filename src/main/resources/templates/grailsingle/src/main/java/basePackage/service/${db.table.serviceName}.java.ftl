package ${project.basePackage}.service;

import ${project.basePackage}.command.cmo.AddProductCmd;
import ${project.basePackage}.command.cmo.PageListProductCmd;
import ${project.basePackage}.command.cmo.UpdateProductCmd;
import ${project.basePackage}.command.co.${db.table.coName};
import com.itgrail.grail.dto.PageResult;

import java.util.List;

public interface ${db.table.serviceName} {

    List<${db.table.coName}> findByLikeName(String name);

    void add(AddProductCmd addProductCmd);

    void update(UpdateProductCmd updateProductCmd);

    PageResult<${db.table.coName}> page(PageListProductCmd pageListProductCmd);

    void delete(String uid);

}
