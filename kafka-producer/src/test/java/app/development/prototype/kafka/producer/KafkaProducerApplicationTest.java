package app.development.prototype.kafka.producer;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.DisabledIf;

@Tag("IntegrationTest")
@DisabledIf(expression = "#{environment['skip.integration.test'] == 'true'}")
@SpringBootTest
class KafkaProducerApplicationTest {

  @Test
  void testMainMethod() {
    KafkaProducerApplication.main(new String[] {""});
    assertTrue(true, "main method executed");
  }
}
