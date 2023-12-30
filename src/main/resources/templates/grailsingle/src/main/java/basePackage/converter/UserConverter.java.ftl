package ${project.basePackage}.converter;

import ${project.basePackage}.command.cmo.AddUserCmd;
import ${project.basePackage}.dataobject.UserDO;
import org.springframework.stereotype.Component;

/**
 * @author grail
 * @date 2020/9/22 10:51 上午
 * @since
 */
@Component
public class UserConverter {

    public UserDO cmdToDO(AddUserCmd addUserCmd){
        UserDO userDO=new UserDO();
        userDO.setName(addUserCmd.getUserName());
        userDO.setRole(addUserCmd.getUserRole());
        return userDO;
    }


}