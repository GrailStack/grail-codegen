package ${project.basePackage}.app.converter;

import ${project.basePackage}.app.command.cmo.AddUserCmd;
import ${project.basePackage}.domain.user.entity.UserE;
import ${project.basePackage}.domain.user.factory.UserFactory;
import com.itgrail.grail.convertor.ConverterI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Grail codegen
 * for demo
 **/
@Component
public class UserClientConverter implements ConverterI {

    @Autowired
    private UserFactory userFactory;

    public UserE clientToEntity(AddUserCmd cmd) {
        UserE userE = userFactory.createUserE();
        userE.setName(cmd.getUserName());
        userE.setRole(cmd.getUserRole());
        return userE;
    }

}
