package com.facouto.ordersservice.command.rest;

import com.facouto.ordersservice.command.commands.CreateOrderCommand;
import com.facouto.ordersservice.command.OrderStatus;
import com.facouto.ordersservice.core.OrderSummary;
import com.facouto.ordersservice.query.FindOrderQuery;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.SubscriptionQueryResult;
import org.hibernate.criterion.Order;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrdersCommandController {

    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;

    @PostMapping
    public OrderSummary save(@Valid @RequestBody CreateOrderRestModel model) {
        CreateOrderCommand command = CreateOrderCommand.builder()
                .orderId(UUID.randomUUID().toString())
                .userId("27b95829-4f3f-4ddf-8983-151ba010e35b")
                .productId(model.getProductId())
                .quantity(model.getQuantity())
                .addressId(model.getAddressId())
                .orderStatus(OrderStatus.CREATED)
                .build();

        SubscriptionQueryResult<OrderSummary, OrderSummary> subscriptionQueryResult = queryGateway.subscriptionQuery(
            new FindOrderQuery(command.getOrderId()),
            ResponseTypes.instanceOf(OrderSummary.class),
            ResponseTypes.instanceOf(OrderSummary.class)
        );

        try {
            commandGateway.sendAndWait(command);
            return subscriptionQueryResult.updates().blockFirst();
        } finally {
            subscriptionQueryResult.close();
        }
    }
}