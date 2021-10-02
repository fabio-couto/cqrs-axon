package com.facouto.ordersservice.command.rest;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
public class CreateOrderRestModel {
    @NotBlank
    private String productId;

    @Min(value=1)
    private int quantity;

    @NotBlank
    private String addressId;
}
