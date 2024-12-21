package ${project.basePackage}.controller;

import ${project.basePackage}.service.${db.table.serviceName};
import ${project.basePackage}.command.cmo.Add${db.table.name}Cmd;
import ${project.basePackage}.command.cmo.PageList${db.table.name}Cmd;
import ${project.basePackage}.command.cmo.Update${db.table.name}Cmd;
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
    private ${db.table.serviceName} ${db.table.name}Service;

    @GetMapping("/list")
    public ResultData list(String name) {
        return ResultData.success(${db.table.name}Service.findByLikeName(name));
    }

    @GetMapping("/page")
    public ResultData page(PageList${db.table.name}Cmd pageList${db.table.name}Cmd) {
        return ResultData.success(${db.table.name}Service.page(pageList${db.table.name}Cmd));
    }

    @PostMapping("/add")
    public ResultData add(@ApiParam(value = "创建相关参数") @RequestBody Add${db.table.name}Cmd add${db.table.name}Cmd) {
        ${db.table.name}Service.add(add${db.table.name}Cmd);
        return ResultData.success();
    }
    
    @PostMapping("/update")
    public ResultData update(@ApiParam(value = "修改相关参数") @RequestBody Update${db.table.name}Cmd update${db.table.name}Cmd) {
        ${db.table.name}Service.update(update${db.table.name}Cmd);
        return ResultData.success();
    }

    @GetMapping("/delete")
    public ResultData delete(String id) {
        ${db.table.name}Service.delete(id);
        return ResultData.success();
    }


}
