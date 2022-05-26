package app.development.prototype.kafka.producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling // added for Scheduling used in SampleProducerServiceImpl.sendScheduledMessage
public class KafkaProducerApplication {

  public static void main(String[] args) {
    SpringApplication.run(KafkaProducerApplication.class, args);
  }
}
