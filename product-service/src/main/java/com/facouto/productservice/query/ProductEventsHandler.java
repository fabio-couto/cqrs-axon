package com.facouto.productservice.query;

import com.facouto.core.events.ProductReservationCanceledEvent;
import com.facouto.core.events.ProductReservedEvent;
import com.facouto.productservice.core.data.ProductEntity;
import com.facouto.productservice.core.events.ProductCreatedEvent;
import com.facouto.productservice.core.data.ProductsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.ResetHandler;
import org.axonframework.messaging.interceptors.ExceptionHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("product-group")
@RequiredArgsConstructor
@Slf4j
public class ProductEventsHandler {

    private final ProductsRepository productRepository;

    @ExceptionHandler(resultType = Exception.class)
    public void handle(Exception ex) throws Exception {
        throw ex;
    }

    @ExceptionHandler(resultType = IllegalArgumentException.class)
    public void handle(IllegalArgumentException ex) {

        log.error("Error", ex);
    }

    @EventHandler
    public void on(ProductCreatedEvent productCreatedEvent) throws Exception {
        ProductEntity productEntity = new ProductEntity();
        BeanUtils.copyProperties(productCreatedEvent, productEntity);
        productRepository.save(productEntity);
    }

    @EventHandler
    public void on(ProductReservedEvent productReservedEvent) {
        ProductEntity productEntity = productRepository.findByProductId(productReservedEvent.getProductId());
        productEntity.setQuantity(productEntity.getQuantity() - productReservedEvent.getQuantity());
        productRepository.save(productEntity);
        log.info(String.format("ProductReservedEvent handled for orderId: %s and productId: %s", productReservedEvent.getOrderId(), productReservedEvent.getProductId()));
    }

    @EventHandler
    public void on(ProductReservationCanceledEvent productReservationCanceledEvent) {
        ProductEntity productEntity = productRepository.findByProductId(productReservationCanceledEvent.getProductId());
        productEntity.setQuantity(productEntity.getQuantity() + productReservationCanceledEvent.getQuantity());
        productRepository.save(productEntity);
    }

    @ResetHandler
    public void reset() {
        productRepository.deleteAll();
    }
}