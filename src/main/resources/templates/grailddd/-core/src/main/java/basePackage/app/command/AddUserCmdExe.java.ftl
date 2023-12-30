package ${project.basePackage}.app.command;

import ${project.basePackage}.app.command.cmo.AddUserCmd;
import ${project.basePackage}.app.converter.UserClientConverter;
import ${project.basePackage}.domain.user.entity.UserE;
import ${project.basePackage}.domain.user.service.UserService;
import com.itgrail.grail.annotation.command.CmdHandler;
import com.itgrail.grail.command.CommandExecutorI;
import com.itgrail.grail.dto.ResultData;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Grail codegen
 * for demo
 **/
@CmdHandler
public class AddUserCmdExe implements CommandExecutorI<ResultData<Long>, AddUserCmd> {

    @Autowired
    private UserService userService;

    @Autowired
    private UserClientConverter userClientConverter;

    @Override
    public ResultData<Long> execute(AddUserCmd addUserCmd) {
        UserE userE = userClientConverter.clientToEntity(addUserCmd);
        try {
            Long userId = userService.addUser(userE);
            return ResultData.success(userId);
        } catch (Exception ex) {
            return ResultData.fail("add user failed");
        }
    }

}
