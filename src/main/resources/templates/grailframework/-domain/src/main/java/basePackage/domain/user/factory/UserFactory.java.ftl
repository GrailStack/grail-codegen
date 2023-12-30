package ${project.basePackage}.domain.user.factory;

import ${project.basePackage}.domain.user.entity.UserE;
import ${project.basePackage}.domain.user.repository.UserRepository;
import com.itgrail.grail.annotation.domain.Factory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Grail codegen
 * for demo
 **/
@Factory
public class UserFactory {

    @Autowired
    private UserRepository userRepository;

    public UserE createUserE() {
        UserE userE = new UserE();
        userE.setUserRepository(userRepository);
        return userE;
    }

}
