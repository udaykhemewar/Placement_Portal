package net.javaguides.springboot.helper;
import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlywayConfig {
    @Bean
    public Flyway flyway() {
        Flyway flyway = Flyway.configure().dataSource("jdbc:postgresql://localhost:5432/employees", "postgres", "postgres").load();
        flyway.migrate();
        return flyway;
    }
}
