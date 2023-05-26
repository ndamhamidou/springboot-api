package com.onlinestore.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {

    private String userId;
    private List<OrderLaptopItemsDto> orderLaptopItemsDtoList;
}
