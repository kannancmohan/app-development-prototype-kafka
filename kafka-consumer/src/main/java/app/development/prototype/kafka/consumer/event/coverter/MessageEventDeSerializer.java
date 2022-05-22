package app.development.prototype.kafka.consumer.event.coverter;

import app.development.prototype.kafka.consumer.event.MessageEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

public class MessageEventDeSerializer implements Deserializer<MessageEvent> {

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public MessageEvent deserialize(final String topic, byte[] data) {
    try {
      return objectMapper.readValue(new String(data, StandardCharsets.UTF_8), MessageEvent.class);
    } catch (IOException e) {
      throw new SerializationException(e);
    }
  }
}
