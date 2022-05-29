package app.development.prototype.kafka.producer.controller;

import app.development.prototype.kafka.producer.service.SampleProducerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SampleController {

  private final SampleProducerService sampleProducerService;

  @GetMapping("/kafka/producer/{message}")
  public ResponseEntity<String> sendMessage(@PathVariable("message") final String message) {
    sampleProducerService.sendMessageEvent(message);
    sampleProducerService.sendMessageEventWithKey(message);
    return ResponseEntity.ok("Event published successfully.");
  }
}
