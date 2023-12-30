package ${project.basePackage}.infrastructure.tunnel.db.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ${project.basePackage}.infrastructure.tunnel.db.dataobject.${db.table.doName};
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Grail codegen
 */
@Mapper
public interface ${db.table.daoName} extends BaseMapper<${db.table.doName}> {

}