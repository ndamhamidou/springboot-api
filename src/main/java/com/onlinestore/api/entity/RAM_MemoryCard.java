package com.onlinestore.api.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "t_rams")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RAM_MemoryCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToMany(mappedBy = "compatibleRams")
    private Set<Laptop> laptops;
}
