package app.development.prototype.kafka.consumer.definition;

import app.development.prototype.kafka.consumer.event.MessageEvent;
import java.util.function.Consumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component("messageEventConsumerWithKey")
@Slf4j
public class MessageEventConsumerWithKey implements Consumer<Message<MessageEvent>> {

  @Override
  public void accept(final Message<MessageEvent> messageEventMessage) {
    final String key =
        messageEventMessage.getHeaders().get(KafkaHeaders.RECEIVED_MESSAGE_KEY, String.class);
    final MessageEvent messageEvent = messageEventMessage.getPayload();
    LOG.info("Received id:{} and event:{}", key, messageEvent);
  }
}
