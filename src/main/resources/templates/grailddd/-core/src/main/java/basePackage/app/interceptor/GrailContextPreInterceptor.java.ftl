package ${project.basePackage}.app.interceptor;

import com.itgrail.grail.annotation.command.PreInterceptor;
import com.itgrail.grail.command.CommandInterceptorI;
import com.itgrail.grail.dto.Command;

/**
 * @author Grail codegen
 * for demo
 */
@PreInterceptor
public class GrailContextPreInterceptor implements CommandInterceptorI {

    @Override
    public void preIntercept(Command command) {

    }

}
