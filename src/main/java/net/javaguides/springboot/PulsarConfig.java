package net.javaguides.springboot;

import net.javaguides.springboot.model.Attendance;
import org.apache.pulsar.client.api.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PulsarConfig {

    @Value("${pulsar.serviceUrl}")
    private String pulsarServiceUrl;

    @Bean
    public Producer<Attendance> attendanceProducer() throws PulsarClientException {
        PulsarClient pulsarClient = PulsarClient.builder()
                .serviceUrl(pulsarServiceUrl)
                .build();

        return pulsarClient.newProducer(Schema.JSON(Attendance.class))
                .topic("attendance-topic")
                .create();
    }
}