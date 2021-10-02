package com.facouto.ordersservice.core.events;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class OrderApprovedEvent {
    private final String orderId;
}
