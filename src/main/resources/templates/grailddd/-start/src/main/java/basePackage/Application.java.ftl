package ${project.basePackage};

import com.itgrail.grail.annotation.domain.Domain;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * @author Grail codegen
 */
@SpringBootApplication
@Domain(code = "${(domain.code)!''}", parentCode = "${(domain.parentCode)!''}", name = "${(domain.name)!''}", desc = "${(domain.desc)!''}")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
