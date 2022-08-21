package com.kcm.msp.dev.app.app.development.prototype.kafka.processor;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kcm.msp.dev.app.app.development.prototype.kafka.processor.event.MessageEvent;
import java.util.Map;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.DisabledIf;

@DisabledIf(expression = "#{environment['skip.integration.test'] == 'true'}")
@SpringBootTest
@EmbeddedKafka(topics = {"testProcessorInTopic", "testProcessorOutTopic"})
@TestPropertySource(
    properties = {
      // bridge between embedded Kafka and Spring Cloud Stream
      "spring.cloud.stream.kafka.binder.brokers=${spring.embedded.kafka.brokers}",
      // using real kafka
      "spring.autoconfigure.exclude=org.springframework.cloud.stream.test.binder.TestSupportBinderAutoConfiguration",
      "spring.cloud.stream.bindings.messageProcessor-in-0.destination=testProcessorInTopic",
      "spring.cloud.stream.bindings.messageProcessor-in-0.group=testProcessorInConsumerGroup",
      "spring.cloud.stream.bindings.messageProcessor-out-0.destination=testProcessorOutTopic",
      "spring.cloud.stream.kafka.binder.configuration.listeners: PLAINTEXT://${spring.embedded.kafka.brokers}"
    })
@DirtiesContext
final class KafkaProcessorIntegrationTest {

  private static final String TEST_PROCESSOR_IN_TOPIC = "testProcessorInTopic";
  private static final String TEST_PROCESSOR_OUT_TOPIC = "testProcessorOutTopic";
  private static final String TEST_UUID = "test-uuid";
  @Autowired private EmbeddedKafkaBroker embeddedKafka;

  @Test
  void testProcessor() throws JsonProcessingException {
    // create a message and send it to processor's input topic
    final MessageEvent event =
        MessageEvent.builder()
            .messageId(TEST_UUID)
            .timeStamp("test-timestamp")
            .message("test-message")
            .build();
    final ProducerRecord<String, String> producerRecord =
        new ProducerRecord<>(
            TEST_PROCESSOR_IN_TOPIC, TEST_UUID, new ObjectMapper().writeValueAsString(event));
    final Producer<String, String> producer = getProducer();
    producer.send(producerRecord);
    producer.flush();

    // create a consumer to check if processor has sent an event to output topic
    final Consumer<String, String> consumer = getConsumer();
    embeddedKafka.consumeFromAnEmbeddedTopic(consumer, TEST_PROCESSOR_OUT_TOPIC);
    final ConsumerRecord<String, String> consumerRecord =
        KafkaTestUtils.getSingleRecord(consumer, TEST_PROCESSOR_OUT_TOPIC);
    assertNotNull(consumerRecord);
    assertNotNull(consumerRecord.value());
    consumer.close();
  }

  private Consumer<String, String> getConsumer() {
    final Map<String, Object> consumerProps =
        KafkaTestUtils.consumerProps("testConsumerGroup", "false", embeddedKafka);
    consumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
    return new DefaultKafkaConsumerFactory<>(
            consumerProps, new StringDeserializer(), new StringDeserializer())
        .createConsumer();
  }

  private Producer<String, String> getProducer() {
    final Map<String, Object> producerProps = KafkaTestUtils.producerProps(embeddedKafka);
    return new DefaultKafkaProducerFactory<>(
            producerProps, new StringSerializer(), new StringSerializer())
        .createProducer();
  }
}
