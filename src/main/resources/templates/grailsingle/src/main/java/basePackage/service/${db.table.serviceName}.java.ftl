package ${project.basePackage}.service;

import ${project.basePackage}.command.cmo.Add${db.table.name}Cmd;
import ${project.basePackage}.command.cmo.PageList${db.table.name}Cmd;
import ${project.basePackage}.command.cmo.Update${db.table.name}Cmd;
import ${project.basePackage}.command.co.${db.table.coName};
import com.itgrail.grail.dto.PageResult;

import java.util.List;

public interface ${db.table.serviceName} {

    List<${db.table.coName}> findByLikeName(String name);

    void add(Add${db.table.name}Cmd add${db.table.name}Cmd);

    void update(Update${db.table.name}Cmd update${db.table.name}Cmd);

    PageResult<${db.table.coName}> page(PageList${db.table.name}Cmd pageList${db.table.name}Cmd);

    void delete(String id);

}
