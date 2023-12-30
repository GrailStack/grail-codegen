package ${project.basePackage}.command.query;

import com.itgrail.grail.command.CommandExecutorWithoutInputI;
import com.itgrail.grail.dto.ResultData;
import ${project.basePackage}.command.co.UserCO;

import java.util.List;

/**
 * @author xujin
 * @date 2020/9/22 10:46 上午
 * @since
 */
public class QueryUserCmdExe implements CommandExecutorWithoutInputI<ResultData<List<UserCO>>> {

    @Override
    public ResultData<List<UserCO>> execute() {
        return null;
    }
}