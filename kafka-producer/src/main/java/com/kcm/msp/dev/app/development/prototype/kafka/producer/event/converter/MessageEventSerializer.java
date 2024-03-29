package com.kcm.msp.dev.app.development.prototype.kafka.producer.event.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kcm.msp.dev.app.development.prototype.kafka.producer.event.MessageEvent;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

public class MessageEventSerializer implements Serializer<MessageEvent> {

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public byte[] serialize(final String topic, final MessageEvent event) {
    try {
      return objectMapper.writeValueAsBytes(event);
    } catch (JsonProcessingException e) {
      throw new SerializationException(e);
    }
  }
}
