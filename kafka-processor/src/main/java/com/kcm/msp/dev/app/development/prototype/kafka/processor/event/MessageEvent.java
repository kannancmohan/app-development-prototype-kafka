package com.kcm.msp.dev.app.development.prototype.kafka.processor.event;

import lombok.Builder;

public record MessageEvent(String messageId, String message, String timeStamp) {
  @Builder
  public MessageEvent {}
}
