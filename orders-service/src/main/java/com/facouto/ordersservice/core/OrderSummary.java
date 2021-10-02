package com.facouto.ordersservice.core;

import com.facouto.ordersservice.command.OrderStatus;
import lombok.Data;

@Data
public class OrderSummary {
    private final String orderId;
    private final OrderStatus orderStatus;
    private final String message;
}