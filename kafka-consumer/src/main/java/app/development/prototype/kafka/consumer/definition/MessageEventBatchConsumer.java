package app.development.prototype.kafka.consumer.definition;

import app.development.prototype.kafka.consumer.event.MessageEvent;
import java.util.List;
import java.util.function.Consumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component("messageEventBatchConsumer")
@Slf4j
public class MessageEventBatchConsumer implements Consumer<List<MessageEvent>> {
  @Override
  public void accept(final List<MessageEvent> messageEvents) {
    for (final MessageEvent messageEvent : messageEvents) {
      LOG.info("Received event:{}", messageEvent);
    }
  }
}
