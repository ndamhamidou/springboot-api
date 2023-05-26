package com.onlinestore.api.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "t_order_laptop_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderLaptopItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String modelName;
    private BigDecimal price;
    private Integer quantity;
}
