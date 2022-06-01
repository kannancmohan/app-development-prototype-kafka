package app.development.prototype.kafka.processor.event.converter;

import app.development.prototype.kafka.processor.event.UpdatedMessageEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

public class UpdatedMessageEventSerializer implements Serializer<UpdatedMessageEvent> {

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public byte[] serialize(final String topic, final UpdatedMessageEvent event) {
    try {
      return objectMapper.writeValueAsBytes(event);
    } catch (JsonProcessingException e) {
      throw new SerializationException(e);
    }
  }
}
