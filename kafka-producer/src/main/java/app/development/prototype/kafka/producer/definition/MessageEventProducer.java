package app.development.prototype.kafka.producer.definition;

import app.development.prototype.kafka.producer.event.MessageEvent;
import java.util.function.Supplier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component("messageEventProducer")
@Slf4j
public class MessageEventProducer implements Supplier<MessageEvent> {

  @Override
  public MessageEvent get() {
    return new MessageEvent("1", "test-message-1");
  }
}
