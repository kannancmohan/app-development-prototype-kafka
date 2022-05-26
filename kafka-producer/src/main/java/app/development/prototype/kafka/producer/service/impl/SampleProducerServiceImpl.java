package app.development.prototype.kafka.producer.service.impl;

import app.development.prototype.kafka.producer.event.MessageEvent;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SampleProducerServiceImpl {

  private static final String PRODUCER_BINDING_NAME = "messageEventProducer-out-0";
  private final StreamBridge streamBridge;

  @Scheduled(cron = "*/2 * * * * *") // sending events at an interval of 2 sec
  public void sendScheduledMessage() {
    final String uuid = UUID.randomUUID().toString();
    final Timestamp timestamp = new Timestamp(new Date().getTime());
    final MessageEvent messageEvent =
        MessageEvent.builder()
            .messageId(uuid)
            .message("scheduled message sent at:" + timestamp)
            .build();
    streamBridge.send(PRODUCER_BINDING_NAME, messageEvent);
  }

  /**
   * method that sends event with key in kafka header.The key is required if the order of the event
   * matters to the consumer
   */
  @Scheduled(cron = "*/10 * * * * *") // sending events at an interval of 10 sec
  public void sendScheduledMessageWithKey() {
    final String uuid = UUID.randomUUID().toString();
    final Timestamp timestamp = new Timestamp(new Date().getTime());
    final MessageEvent event =
        MessageEvent.builder()
            .messageId(uuid)
            .message("scheduled message sent at:" + timestamp)
            .build();
    final Message<MessageEvent> eventMessage =
        MessageBuilder.withPayload(event)
            .setHeader(KafkaHeaders.MESSAGE_KEY, event.messageId().getBytes(StandardCharsets.UTF_8))
            .build();
    streamBridge.send(PRODUCER_BINDING_NAME, eventMessage);
  }
}
