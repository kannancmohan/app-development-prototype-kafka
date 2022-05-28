package app.development.prototype.kafka.producer.service;

public interface SampleProducerService {
  void sendMessageEvent(String message);

  void sendMessageEventWithKey(String message);
}
