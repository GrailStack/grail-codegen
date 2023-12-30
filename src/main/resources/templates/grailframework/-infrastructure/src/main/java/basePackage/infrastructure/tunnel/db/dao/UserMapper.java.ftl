package ${project.basePackage}.infrastructure.tunnel.db.dao;

<#if project.dbConfigure>

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ${project.basePackage}.infrastructure.tunnel.db.dataobject.UserDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Grail codegen
 * for demo
 **/
@Mapper
public interface UserMapper extends BaseMapper<UserDO> {

}
<#else>
 public interface UserMapper {

 }
</#if>