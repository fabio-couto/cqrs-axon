package com.facouto.productservice.command.interceptors;

import com.facouto.productservice.command.CreateProductCommand;
import com.facouto.productservice.core.data.ProductLookupEntity;
import com.facouto.productservice.core.data.ProductLookupRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.BiFunction;

@RequiredArgsConstructor
@Component
@Slf4j
public class CreateProductCommandInterceptor implements MessageDispatchInterceptor<CommandMessage<?>> {

    private final ProductLookupRepository productLookupRepository;

    @Override
    public BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> handle(List<? extends CommandMessage<?>> list) {
        return (index, command) -> {

            log.info("Intercepted command: " + command.getPayloadType());

            if (CreateProductCommand.class.equals(command.getPayloadType())) {

                CreateProductCommand createProductCommand = (CreateProductCommand)command.getPayload();

                ProductLookupEntity lookupEntity = productLookupRepository.findByProductIdOrTitle(createProductCommand.getProductId(),
                        createProductCommand.getTitle());

                if (lookupEntity != null) {
                    throw new IllegalStateException(String.format("Product with productId %s or title %s already exists",
                        createProductCommand.getProductId(),
                        createProductCommand.getTitle()
                    ));
                }

            }

            return command;
        };
    }
}