package ${project.basePackage}.domain.user.service.impl;

import ${project.basePackage}.domain.user.entity.UserE;
import ${project.basePackage}.domain.user.repository.UserRepository;
import ${project.basePackage}.domain.user.service.UserService;
import com.itgrail.grail.annotation.domain.DomainService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Grail codegen
 * for dmeo
 **/
@DomainService
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Long addUser(UserE userE) {
        return userRepository.addUser(userE);
    }

}
