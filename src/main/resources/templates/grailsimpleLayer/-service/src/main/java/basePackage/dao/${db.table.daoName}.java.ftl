package ${project.basePackage}.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ${project.basePackage}.dataobject.${db.table.doName};
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Grail codegen
 */
@Mapper
public interface ${db.table.daoName} extends BaseMapper<${db.table.doName}> {

}