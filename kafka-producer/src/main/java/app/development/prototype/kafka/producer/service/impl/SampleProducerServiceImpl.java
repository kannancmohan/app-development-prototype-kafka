package app.development.prototype.kafka.producer.service.impl;

import app.development.prototype.kafka.producer.event.MessageEvent;
import app.development.prototype.kafka.producer.service.SampleProducerService;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SampleProducerServiceImpl implements SampleProducerService {
  private static final String PRODUCER_BINDING_NAME = "messageEventProducer-out-0";
  private final StreamBridge streamBridge;

  @Override
  public void sendMessageEvent(final String message) {
    final MessageEvent messageEvent = generateMessageEvent(message);
    streamBridge.send(PRODUCER_BINDING_NAME, messageEvent);
  }

  /**
   * method that sends event with key in kafka header.The key is required if the order of the event
   * matters to the consumer
   */
  @Override
  public void sendMessageEventWithKey(final String message) {
    final MessageEvent event = generateMessageEvent(message);
    final Message<MessageEvent> eventMessage =
        MessageBuilder.withPayload(event)
            .setHeader(KafkaHeaders.MESSAGE_KEY, event.messageId().getBytes(StandardCharsets.UTF_8))
            .build();
    streamBridge.send(PRODUCER_BINDING_NAME, eventMessage);
  }

  private MessageEvent generateMessageEvent(final String message) {
    final String uuid = UUID.randomUUID().toString();
    final Timestamp timestamp = new Timestamp(new Date().getTime());
    return MessageEvent.builder()
        .messageId(uuid)
        .timeStamp(timestamp.toString())
        .message(message)
        .build();
  }
}
