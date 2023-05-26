package com.onlinestore.api.dto;

import com.onlinestore.api.entity.Laptop;
import lombok.*;

import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class SaveRamRequest {

    private String name;
    private Set<Laptop> laptops;
}
