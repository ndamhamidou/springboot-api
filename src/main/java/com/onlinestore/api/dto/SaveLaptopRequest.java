package com.onlinestore.api.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class SaveLaptopRequest {

    private String modelName;
    private int availableQty;
}
