package ch.api.onlyquest;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "OnlyQuest",
				version = "1.0",
				description = "RESTful API for Quest game",
				termsOfService = "",
				contact = @Contact(
						name = "Mr. Lambert",
						email = "1312core@gmail.com"
				)
		)
)
public class OnlyquestApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnlyquestApplication.class, args);
	}

}
