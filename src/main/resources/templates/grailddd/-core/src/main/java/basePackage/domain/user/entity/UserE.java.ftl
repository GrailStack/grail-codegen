package ${project.basePackage}.domain.user.entity;

import ${project.basePackage}.domain.user.repository.UserRepository;
import com.itgrail.grail.annotation.domain.Entity;

import lombok.Data;

/**
 * @author Grail codegen
 * for demo
 **/
@Entity
@Data
public class UserE {

    private UserRepository userRepository;

    private Long id;

    private String name;

    private String role;

}
