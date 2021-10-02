package com.facouto.paymentservice.query;

import com.facouto.core.events.PaymentProcessedEvent;
import lombok.RequiredArgsConstructor;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@ProcessingGroup("payment-group")
public class PaymentsEventsHandler {

    private final PaymentsRepository paymentsRepository;

    @EventHandler
    public void on(PaymentProcessedEvent paymentProcessedEvent) {
        PaymentEntity paymentEntity = new PaymentEntity();
        BeanUtils.copyProperties(paymentProcessedEvent, paymentEntity);
        paymentsRepository.save(paymentEntity);
    }
}
