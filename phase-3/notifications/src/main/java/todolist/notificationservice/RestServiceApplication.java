package todolist.notificationservice;

/*import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;*/
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
/*@OpenAPIDefinition(
        info = @Info(
                title = "ToDoList API",
                version = "1.0",
                description = "ToDoList API"
        )
)*/
@ComponentScan(basePackages = "todolist.*")
public class RestServiceApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(RestServiceApplication.class);
        application.setWebApplicationType(WebApplicationType.NONE);
        application.run(args);
    }

}
