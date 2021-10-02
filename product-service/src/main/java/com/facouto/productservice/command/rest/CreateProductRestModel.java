package com.facouto.productservice.command.rest;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Data
public class CreateProductRestModel {

    @NotBlank
    @Length(max = 50)
    private String title;

    @DecimalMin(value = "0.01")
    private BigDecimal price;

    @Min(value = 1)
    private Integer quantity;
}