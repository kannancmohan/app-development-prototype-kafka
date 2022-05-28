package app.development.prototype.kafka.consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

import app.development.prototype.kafka.consumer.definition.MessageEventConsumer;
import app.development.prototype.kafka.consumer.definition.MessageEventWithKeyConsumer;
import app.development.prototype.kafka.consumer.event.MessageEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.messaging.Message;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.DisabledIf;

@DisabledIf(expression = "#{environment['skip.integration.test'] == 'true'}")
@SpringBootTest
@EmbeddedKafka
@TestPropertySource(
    properties = {
      // bridge between embedded Kafka and Spring Cloud Stream
      "spring.cloud.stream.kafka.binder.brokers=${spring.embedded.kafka.brokers}",
      // using real kafka
      "spring.autoconfigure.exclude=org.springframework.cloud.stream.test.binder.TestSupportBinderAutoConfiguration",
      "spring.cloud.stream.bindings.messageEventConsumer-in-0.destination=testConsumerEmbeddedTopic",
      "spring.cloud.stream.bindings.messageEventBatchConsumer-in-0.destination=testConsumerEmbeddedTopic",
      "spring.cloud.stream.bindings.messageEventWithKeyConsumer-in-0.destination=testConsumerEmbeddedTopic",
      "spring.cloud.stream.kafka.binder.configuration.listeners: PLAINTEXT://${spring.embedded.kafka.brokers}"
    })
@DirtiesContext
final class KafkaConsumerIntegrationTest {

  public static final String TEST_TOPIC = "testConsumerEmbeddedTopic";
  @Autowired private EmbeddedKafkaBroker embeddedKafka;
  @SpyBean private MessageEventConsumer messageEventConsumer;
  @SpyBean private MessageEventWithKeyConsumer messageEventWithKeyConsumer;
  @Captor ArgumentCaptor<MessageEvent> messageEventArgumentCaptor;
  @Captor ArgumentCaptor<Message<MessageEvent>> messageEventWithKeyArgumentCaptor;

  @Test
  void testConsumers() throws JsonProcessingException {
    final KafkaTemplate<String, String> producer = buildKafkaTemplate();
    producer.setDefaultTopic(TEST_TOPIC);
    final MessageEvent messageEvent =
        MessageEvent.builder()
            .messageId("test-uuid")
            .timeStamp("test-timestamp")
            .message("test-message")
            .build();
    producer.sendDefault(new ObjectMapper().writeValueAsString(messageEvent));
    producer.flush();

    verify(messageEventConsumer, timeout(5000).times(1))
        .accept(messageEventArgumentCaptor.capture());
    verify(messageEventWithKeyConsumer, timeout(5000).times(1))
        .accept(messageEventWithKeyArgumentCaptor.capture());
    final MessageEvent messageEventReceived = messageEventArgumentCaptor.getValue();
    final Message<MessageEvent> messageEventWithKeyReceived =
        messageEventWithKeyArgumentCaptor.getValue();
    assertNotNull(messageEventReceived);
    assertNotNull(messageEventWithKeyReceived);
    assertEquals("test-uuid", messageEventReceived.messageId());
    assertEquals("test-uuid", messageEventWithKeyReceived.getPayload().messageId());
  }

  private KafkaTemplate<String, String> buildKafkaTemplate() {
    final Map<String, Object> producerProps = KafkaTestUtils.producerProps(embeddedKafka);
    producerProps.put("key.serializer", StringSerializer.class);
    producerProps.put("value.serializer", StringSerializer.class);
    ProducerFactory<String, String> pf = new DefaultKafkaProducerFactory<>(producerProps);
    return new KafkaTemplate<>(pf);
  }
}
