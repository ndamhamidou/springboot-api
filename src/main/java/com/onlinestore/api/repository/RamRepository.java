package com.onlinestore.api.repository;

import com.onlinestore.api.entity.Laptop;
import com.onlinestore.api.entity.RAM_MemoryCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RamRepository extends JpaRepository<RAM_MemoryCard, Long> {

    List<RAM_MemoryCard> findAllByLaptopsContaining(Laptop laptop);
}
