package com.facouto.paymentservice.command;

import com.facouto.core.commands.ProcessPaymentCommand;
import com.facouto.core.events.PaymentProcessedEvent;
import com.google.common.base.Strings;
import lombok.Data;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Data
@Aggregate
public class PaymentAggregate {
    @AggregateIdentifier
    private String paymentId;
    private String orderId;

    @CommandHandler
    public PaymentAggregate(ProcessPaymentCommand processPaymentCommand)
    {
        if (Strings.isNullOrEmpty(processPaymentCommand.getPaymentId()))
            throw new IllegalArgumentException("PaymentId cannot be empty");

        if (Strings.isNullOrEmpty(processPaymentCommand.getOrderId()))
            throw new IllegalArgumentException("OrderId cannot be empty");

        PaymentProcessedEvent paymentProcessedEvent = PaymentProcessedEvent.builder()
                        .paymentId(processPaymentCommand.getPaymentId())
                        .orderId(processPaymentCommand.getOrderId())
                        .build();

        AggregateLifecycle.apply(paymentProcessedEvent);
    }

    @EventSourcingHandler
    public void on(PaymentProcessedEvent paymentProcessedEvent) {
        this.paymentId = paymentProcessedEvent.getPaymentId();
        this.orderId = paymentProcessedEvent.getOrderId();
    }
}