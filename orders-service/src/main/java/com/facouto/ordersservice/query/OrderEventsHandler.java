package com.facouto.ordersservice.query;

import com.facouto.core.events.OrderRejectedEvent;
import com.facouto.ordersservice.command.OrderStatus;
import com.facouto.ordersservice.command.commands.RejectOrderCommand;
import com.facouto.ordersservice.core.data.OrderEntity;
import com.facouto.ordersservice.core.data.OrdersRepository;
import com.facouto.ordersservice.core.events.OrderApprovedEvent;
import com.facouto.ordersservice.core.events.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@ProcessingGroup("order-group")
public class OrderEventsHandler {

    private final OrdersRepository ordersRepository;

    @EventHandler
    public void on(OrderCreatedEvent event) {
        OrderEntity entity = new OrderEntity();
        BeanUtils.copyProperties(event, entity);
        ordersRepository.save(entity);
    }

    @EventHandler
    public void on(OrderApprovedEvent event) {
        OrderEntity entity = ordersRepository.getById(event.getOrderId());
        entity.setOrderStatus(OrderStatus.APPROVED);
        ordersRepository.save(entity);
    }

    @EventHandler
    public void on(OrderRejectedEvent event) {
        OrderEntity entity = ordersRepository.getById(event.getOrderId());
        entity.setOrderStatus(OrderStatus.REJECTED);
        ordersRepository.save(entity);
    }
}