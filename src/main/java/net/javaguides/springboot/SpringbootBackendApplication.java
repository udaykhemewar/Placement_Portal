package net.javaguides.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
@EnableCaching
@EnableAsync
public class SpringbootBackendApplication {

    public static void main(String[] args) {

        SpringApplication.run(SpringbootBackendApplication.class, args);
    }

}
