package app.development.prototype.kafka.producer.definition;

import app.development.prototype.kafka.producer.event.MessageEvent;
import java.util.function.Function;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component("messageEventProducer")
@Slf4j
public class MessageEventProducer implements Function<MessageEvent, MessageEvent> {

  @Override
  public MessageEvent apply(final MessageEvent event) {
    LOG.info("Producer sending message :{}", event);
    return event;
  }
}
