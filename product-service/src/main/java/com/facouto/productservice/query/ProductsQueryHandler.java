package com.facouto.productservice.query;

import com.facouto.productservice.core.data.ProductsRepository;
import com.facouto.productservice.query.rest.ProductRestModel;
import lombok.RequiredArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProductsQueryHandler {

    private final ProductsRepository productsRepository;

    @QueryHandler
    public List<ProductRestModel> findProducts(FindProductsQuery findProductsQuery) {
        return productsRepository.findAll()
                                 .stream()
                                 .map(i -> {
                                     ProductRestModel model = new ProductRestModel();
                                     BeanUtils.copyProperties(i, model);
                                     return model;
                                 })
                                 .collect(Collectors.toList());
    }
}