package com.facouto.productservice.command.rest;

import com.facouto.productservice.command.CreateProductCommand;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductsCommandController {

    private final CommandGateway commandGateway;

    @PostMapping
    public String save(@Valid @RequestBody CreateProductRestModel product) {

        CreateProductCommand command = CreateProductCommand.builder()
                                        .price(product.getPrice())
                                        .title(product.getTitle())
                                        .quantity(product.getQuantity())
                                        .productId(UUID.randomUUID().toString())
                                        .build();

        return commandGateway.sendAndWait(command);
    }
}