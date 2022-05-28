package app.development.prototype.kafka.producer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import app.development.prototype.kafka.producer.service.SampleProducerService;
import java.time.Duration;
import java.util.Collections;
import java.util.Map;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@EmbeddedKafka
@TestPropertySource(
    properties = {
      // bridge between embedded Kafka and Spring Cloud Stream
      "spring.cloud.stream.kafka.binder.brokers=${spring.embedded.kafka.brokers}",
      // using real kafka
      "spring.autoconfigure.exclude=org.springframework.cloud.stream.test.binder.TestSupportBinderAutoConfiguration",
      "spring.cloud.stream.bindings.messageEventProducer-out-0.destination=testEmbeddedTopic",
      "spring.cloud.stream.kafka.binder.configuration.listeners: PLAINTEXT://${spring.embedded.kafka.brokers}"
    })
@DirtiesContext
public class KafkaProducerIntegrationTest {

  @Autowired private SampleProducerService sampleProducerService;
  public static final String TEST_TOPIC = "testEmbeddedTopic";
  private static final String TEST_CONSUMER_GROUP = "testConsumerGroup";

  @Test
  void testSendReceive(@Autowired EmbeddedKafkaBroker embeddedKafka) {
    sampleProducerService.sendMessageEventWithKey("test message");
    sampleProducerService.sendMessageEvent("test message");

    Map<String, Object> consumerProps =
        KafkaTestUtils.consumerProps(TEST_CONSUMER_GROUP, "false", embeddedKafka);
    consumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
    consumerProps.put("key.deserializer", StringDeserializer.class);
    consumerProps.put("value.deserializer", StringDeserializer.class);
    DefaultKafkaConsumerFactory<String, String> consumerFactory =
        new DefaultKafkaConsumerFactory<>(consumerProps);

    Consumer<String, String> consumer = consumerFactory.createConsumer();
    consumer.assign(Collections.singleton(new TopicPartition(TEST_TOPIC, 0)));
    ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(10));
    consumer.commitSync();
    assertEquals(2, records.count());
  }
}
