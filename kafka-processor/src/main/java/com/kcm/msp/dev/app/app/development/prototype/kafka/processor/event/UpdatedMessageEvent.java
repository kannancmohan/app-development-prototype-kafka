package com.kcm.msp.dev.app.app.development.prototype.kafka.processor.event;

import lombok.Builder;

public record UpdatedMessageEvent(
    String messageId, String message, String timeStamp, String update) {
  @Builder
  public UpdatedMessageEvent {}
}
