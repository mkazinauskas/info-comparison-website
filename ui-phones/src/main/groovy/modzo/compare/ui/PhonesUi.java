package modzo.compare.ui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class PhonesUi {

    public static void main(String[] args) {
        SpringApplication.run(PhonesUi.class, args);
    }
}
