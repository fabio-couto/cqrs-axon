package com.facouto.ordersservice.core.data;

import com.facouto.ordersservice.command.OrderStatus;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(schema = "query", name = "orders")
@Data
public class OrderEntity implements Serializable {

    @Id
    @Column(unique = true)
    public String orderId;

    private String productId;

    private String userId;

    private int quantity;

    private String addressId;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

}
