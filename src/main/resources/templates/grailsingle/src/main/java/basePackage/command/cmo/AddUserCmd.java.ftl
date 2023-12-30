package ${project.basePackage}.command.cmo;

import com.itgrail.grail.dto.Command;
import lombok.Data;

/**
 * @author Grail codegen
 * for demo
 **/
@Data
public class AddUserCmd extends Command {
    private String userName;
    private String userRole;
}
