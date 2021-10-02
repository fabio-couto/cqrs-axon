package com.facouto.core.events;

import lombok.Value;

@Value
public class OrderRejectedEvent {
    private final String orderId;
    private final String reason;
}
