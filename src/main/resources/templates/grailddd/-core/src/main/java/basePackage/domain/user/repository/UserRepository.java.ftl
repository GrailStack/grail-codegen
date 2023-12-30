package ${project.basePackage}.domain.user.repository;

import ${project.basePackage}.domain.user.ports.UserPort;
import ${project.basePackage}.domain.user.entity.UserE;
import com.itgrail.grail.annotation.domain.DomainRepository;
import org.springframework.beans.factory.annotation.Autowired;



/**
 * @author Grail codegen
 * for demo
 **/
@DomainRepository
public class UserRepository {


    @Autowired
    private UserPort userPort;

    public Long addUser(UserE userE) {

        return 1L;
    }

}
