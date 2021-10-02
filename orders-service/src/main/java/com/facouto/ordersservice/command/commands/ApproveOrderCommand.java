package com.facouto.ordersservice.command.commands;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@RequiredArgsConstructor
public class ApproveOrderCommand {
    @TargetAggregateIdentifier
    private final String orderId;
}
