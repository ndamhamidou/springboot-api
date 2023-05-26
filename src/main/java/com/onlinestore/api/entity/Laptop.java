package com.onlinestore.api.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "t_laptops")
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Laptop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "model", unique = true)
    private String modelName;
    @Column(name = "quantity")
    private int availableQty;
    private BigDecimal price;
    @ManyToMany
    private Set<RAM_MemoryCard> compatibleRams;
}
