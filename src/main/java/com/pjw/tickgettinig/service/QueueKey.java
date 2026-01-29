package com.pjw.tickgettinig.service;

import lombok.Getter;

@Getter
public enum QueueKey {
  WAIT("waiting key") {
    @Override
    public String of(Object id) {
      return "ticket:queue:" + id;
    }
  },
  PROC("processing key") {
    @Override
    public String of(Object id) {
      return "ticket:processing:" + id;
    }
  },
  PAY("payment key") {
    @Override
    public String of(Object id) {
      return "ticket:payment:" + id;
    }
  };

  private final String key;

  QueueKey(String key) {
    this.key = key;
  }

  public abstract String of(Object id);
}
