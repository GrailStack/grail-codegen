package ${project.basePackage}.controller;

import ${project.basePackage}.service.${db.table.serviceName};
import ${project.basePackage}.command.cmo.AddProductCmd;
import ${project.basePackage}.command.cmo.PageListProductCmd;
import ${project.basePackage}.command.cmo.UpdateProductCmd;
import com.itgrail.grail.dto.ResultData;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 产品管理
 */
@RestController
@RequestMapping("/api/v1/product")
public class ${db.table.controllerName} {

    @Autowired
    private ${db.table.serviceName} service;

    @GetMapping("/list")
    public ResultData list(String name) {
        return ResultData.success(service.findByLikeName(name));
    }

    @GetMapping("/page")
    public ResultData page(PageListProductCmd pageListProductCmd) {
        return ResultData.success(service.page(pageListProductCmd));
    }

    @PostMapping("/add")
    public ResultData add(@ApiParam(value = "创建相关参数") @RequestBody AddProductCmd addProductCmd) {
        service.add(addProductCmd);
        return ResultData.success();
    }
    
    @PostMapping("/update")
    public ResultData update(@ApiParam(value = "修改相关参数") @RequestBody UpdateProductCmd updateProductCmd) {
        service.update(updateProductCmd);
        return ResultData.success();
    }

    @GetMapping("/delete")
    public ResultData delete(String id) {
        service.delete(id);
        return ResultData.success();
    }


}
