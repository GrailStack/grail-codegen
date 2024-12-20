package ${project.basePackage}.service;

import ${project.basePackage}.command.cmo.AddProductCmd;
import ${project.basePackage}.command.cmo.PageListProductCmd;
import ${project.basePackage}.command.cmo.UpdateProductCmd;
import ${project.basePackage}.command.co.ProductCO;
import com.itgrail.grail.dto.PageResult;

import java.util.List;

public interface ${db.table.serviceName} {

    List<ProductCO> findByLikeName(String name);

    void add(AddProductCmd addProductCmd);

    void update(UpdateProductCmd updateProductCmd);

    PageResult<ProductCO> page(PageListProductCmd pageListProductCmd);

    void delete(String uid);

}
