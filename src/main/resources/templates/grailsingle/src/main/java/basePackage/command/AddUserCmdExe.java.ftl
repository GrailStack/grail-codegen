package ${project.basePackage}.command;

import ${project.basePackage}.command.cmo.AddUserCmd;
import ${project.basePackage}.service.UserService;
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

    @Override
    public ResultData<Long> execute(AddUserCmd addUserCmd) {
        try {
            userService.addUser(addUserCmd);
            return ResultData.success(1L);
        } catch (Exception ex) {
            return ResultData.fail("add user failed");
        }
    }

}
