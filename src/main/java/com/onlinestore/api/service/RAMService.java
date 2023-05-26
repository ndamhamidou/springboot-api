package com.onlinestore.api.service;

import com.onlinestore.api.entity.Laptop;
import com.onlinestore.api.entity.RAM_MemoryCard;
import com.onlinestore.api.repository.RamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RAMService {

    private final RamRepository ramRepository;

    public List<RAM_MemoryCard> findAllByLaptopsContaining(Laptop laptop) {
        return ramRepository.findAllByLaptopsContaining(laptop);
    }
}
