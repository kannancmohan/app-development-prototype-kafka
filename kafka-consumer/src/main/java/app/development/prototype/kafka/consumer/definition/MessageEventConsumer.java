package app.development.prototype.kafka.consumer.definition;

import app.development.prototype.kafka.consumer.event.MessageEvent;
import java.util.function.Consumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component("messageEventConsumer")
@Slf4j
public class MessageEventConsumer implements Consumer<MessageEvent> {

  @Override
  public void accept(final MessageEvent messageEvent) {
    LOG.info("Received event:{}", messageEvent);
  }
}
