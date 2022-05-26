package app.development.prototype.kafka.producer.service.impl;

import app.development.prototype.kafka.producer.event.MessageEvent;
import java.sql.Timestamp;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SampleProducerServiceImpl {

  private static final String PRODUCER_BINDING_NAME = "messageEventProducer-out-0";
  private final StreamBridge streamBridge;

  // sending messages at an interval of 2 sec
  @Scheduled(cron = "*/2 * * * * *")
  public void sendScheduledMessage() {
    final Timestamp timestamp = new Timestamp(new Date().getTime());
    final MessageEvent messageEvent =
        MessageEvent.builder()
            .messageId("id")
            .message("scheduled message sent at:" + timestamp)
            .build();
    streamBridge.send(PRODUCER_BINDING_NAME, messageEvent);
  }
}
