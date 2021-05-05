package modzo.compare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class CommandPhonesApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommandPhonesApplication.class, args);
	}
}
