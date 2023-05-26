package com.onlinestore.api.repository;

import com.onlinestore.api.entity.Laptop;
import com.onlinestore.api.entity.RAM_MemoryCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface LaptopRepository extends JpaRepository<Laptop, Long> {

    Optional<Laptop> findByModelName(String model);

    List<Laptop> findLaptopsByCompatibleRamsContaining(RAM_MemoryCard ramMemoryCard);
}
