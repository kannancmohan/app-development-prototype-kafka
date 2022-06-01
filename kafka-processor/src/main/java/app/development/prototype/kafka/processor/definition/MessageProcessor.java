package app.development.prototype.kafka.processor.definition;

import app.development.prototype.kafka.processor.event.MessageEvent;
import app.development.prototype.kafka.processor.event.UpdatedMessageEvent;
import java.util.function.Function;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component("messageProcessor")
@Slf4j
public class MessageProcessor implements Function<MessageEvent, UpdatedMessageEvent> {

  @Override
  public UpdatedMessageEvent apply(final MessageEvent event) {
    LOG.info("Received MessageEvent:{}", event);
    final String updatedMessage = event.message() + ":" + event.timeStamp();
    return UpdatedMessageEvent.builder()
        .messageId(event.messageId())
        .message(event.message())
        .timeStamp(event.timeStamp())
        .update(updatedMessage)
        .build();
  }
}
