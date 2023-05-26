package com.onlinestore.api.dto;

import com.onlinestore.api.entity.RAM_MemoryCard;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LaptopRamCompatible {

    private String modelName;
    private List<RAM_MemoryCard> compatibleRams;
}
