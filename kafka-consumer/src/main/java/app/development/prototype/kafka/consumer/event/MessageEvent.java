package app.development.prototype.kafka.consumer.event;

import lombok.Builder;

public record MessageEvent(String messageId, String message, String timeStamp) {
  @Builder
  public MessageEvent {}
}
