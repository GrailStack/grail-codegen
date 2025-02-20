package ${project.basePackage};

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import com.itgrail.grail.annotation.domain.Domain;

/**
 * @author Grail codegen
 */
@SpringBootApplication
<#--@EnableEurekaClient-->
@Domain(code = "${(domain.code)!''}", parentCode = "${(domain.parentCode)!''}", name = "${(domain.name)!''}", desc = "${(domain.desc)!''}")
<#--@EnableFeignClients-->
public class Application extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
