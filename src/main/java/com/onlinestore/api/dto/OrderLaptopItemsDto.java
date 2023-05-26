package com.onlinestore.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderLaptopItemsDto {
    private Long id;
    private String modelName;
    private BigDecimal price;
    private Integer quantity;
}
