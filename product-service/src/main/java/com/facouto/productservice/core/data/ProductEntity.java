package com.facouto.productservice.core.data;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Entity
@Table(schema = "query", name="product")
public class ProductEntity implements Serializable {

    @Id
    @Column(unique = true)
    private String productId;

    private String title;

    private BigDecimal price;

    private Integer quantity;

}
