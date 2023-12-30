package ${project.basePackage}.app.context;

import lombok.Data;

/**
 * @author Grail codegen
 * for demo
 */
@Data
public class UserContext {
    private String operator;
    private String loginUserId;
    private String loginUserName;
    private String loginUserRole;
    private String loginUserPrivilege;
}
