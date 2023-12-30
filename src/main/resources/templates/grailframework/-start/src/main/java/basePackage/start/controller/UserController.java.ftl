package ${project.basePackage}.start.controller;

import ${project.basePackage}.app.command.cmo.AddUserCmd;
import com.itgrail.grail.command.CommandBus;
import com.itgrail.grail.dto.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Grail codegen
 * for demo
 **/
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private CommandBus commandBus;

    @PostMapping("/add")
    public ResultData<Long> addUser(@RequestBody AddUserCmd addUserCmd) {
        return commandBus.send(addUserCmd);
    }

}
