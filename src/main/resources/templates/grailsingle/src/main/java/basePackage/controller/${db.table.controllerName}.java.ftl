package ${project.basePackage}.controller;

import ${project.basePackage}.service.ProductService ;
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
    private ${db.table.serviceName} productService;

    @GetMapping("/list")
    public ResultData list(String name) {
        return ResultData.success(productService.findByLikeName(name));
    }

    @GetMapping("/page")
    public ResultData page(PageListProductCmd pageListProductCmd) {
        return ResultData.success(productService.page(pageListProductCmd));
    }

    @PostMapping("/add")
    public ResultData add(@ApiParam(value = "创建相关参数") @RequestBody AddProductCmd addProductCmd) {
        productService.add(addProductCmd);
        return ResultData.success();
    }
    
    @PostMapping("/update")
    public ResultData update(@ApiParam(value = "修改相关参数") @RequestBody UpdateProductCmd updateProductCmd) {
        productService.update(updateProductCmd);
        return ResultData.success();
    }

    @GetMapping("/delete")
    public ResultData delete(String id) {
        productService.delete(id);
        return ResultData.success();
    }


}
