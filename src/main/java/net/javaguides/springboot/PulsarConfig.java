package net.javaguides.springboot;
import net.javaguides.springboot.repository.AttendanceRepository;
import net.javaguides.springboot.model.Attendance;
import org.apache.pulsar.client.api.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PulsarConfig {
    private final AttendanceRepository attendanceRepository;
    @Value("${pulsar.serviceUrl}")
    private String pulsarServiceUrl;

    public PulsarConfig(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }

    @Bean
    public Producer<Attendance> attendanceProducer() throws PulsarClientException {
        PulsarClient pulsarClient = PulsarClient.builder()
                .serviceUrl(pulsarServiceUrl)
                .build();

        return pulsarClient.newProducer(Schema.JSON(Attendance.class))
                .topic("attendance-topic")
                .create();
    }
@Bean
//    @Async
//    @Scheduled(fixedDelay = 1000)
public Consumer<Attendance> attendanceConsumer() throws PulsarClientException {
    PulsarClient pulsarClient = PulsarClient.builder()
            .serviceUrl(pulsarServiceUrl)
            .build();

    Consumer<Attendance> consumer = pulsarClient.newConsumer(Schema.JSON(Attendance.class))
            .subscriptionName("your-subscription-name")
            .subscriptionType(SubscriptionType.Shared)
            .topic("persistent://public/default/attendance-topic")
            .subscribe();

    new Thread(() -> {
        while (true) {
            try {
                Message<Attendance> msg =   consumer.receive();
                Attendance attendance = msg.getValue();
                saveAttendanceToDatabase(attendance);
                consumer.acknowledge(msg);
                System.out.println("Received message: " + msg.getValue());
            } catch (PulsarClientException e) {
                e.printStackTrace();
            }
        }
    }).start();
    return consumer;
}

    private void saveAttendanceToDatabase(Attendance attendance) {
        try {
            attendanceRepository.save(attendance);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
