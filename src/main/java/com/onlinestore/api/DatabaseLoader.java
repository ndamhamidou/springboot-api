package com.onlinestore.api;

import com.onlinestore.api.entity.Laptop;
import com.onlinestore.api.entity.RAM_MemoryCard;
import com.onlinestore.api.repository.LaptopRepository;
import com.onlinestore.api.repository.RamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class DatabaseLoader {

    private final LaptopRepository laptopRepository;
    private final RamRepository ramRepository;

    @Bean
    public CommandLineRunner initializeDatabase(){
        return args -> {
            // initialize ram data
            var ramMemoryCard1 = RAM_MemoryCard.builder()
                    .name("ram1")
                    .build();
            var ramMemoryCard2 = RAM_MemoryCard.builder()
                    .name("ram2")
                    .build();
            var ramMemoryCard3 = RAM_MemoryCard.builder()
                    .name("ram3")
                    .build();
            var ramMemoryCard4 = RAM_MemoryCard.builder()
                    .name("ram4")
                    .build();
            var ramMemoryCard5 = RAM_MemoryCard.builder()
                    .name("ram5")
                    .build();
            ramRepository.saveAll(List.of(ramMemoryCard1, ramMemoryCard2, ramMemoryCard3, ramMemoryCard4, ramMemoryCard5));
            log.info("ram saved !");

            // initialize laptops data

            var laptop1 = Laptop.builder()
                    .modelName("hp")
                    .availableQty(10)
                    .price(new BigDecimal(5500))
                    .compatibleRams(Set.of(ramMemoryCard1, ramMemoryCard2, ramMemoryCard4))
                    .build();

            var laptop2 = Laptop.builder()
                    .modelName("acer")
                    .availableQty(5)
                    .price(new BigDecimal(5250))
                    .compatibleRams(Set.of(ramMemoryCard2, ramMemoryCard3, ramMemoryCard4))
                    .build();

            var laptop3 = Laptop.builder()
                    .modelName("dell")
                    .availableQty(7)
                    .price(new BigDecimal(5100))
                    .compatibleRams(Set.of(ramMemoryCard1, ramMemoryCard3))
                    .build();

            var laptop4 = Laptop.builder()
                    .modelName("compaq")
                    .availableQty(10)
                    .price(new BigDecimal(4600))
                    .compatibleRams(Set.of(ramMemoryCard3, ramMemoryCard2))
                    .build();

            var laptop5 = Laptop.builder()
                    .modelName("asus")
                    .availableQty(2)
                    .price(new BigDecimal(5200))
                    .compatibleRams(Set.of(ramMemoryCard1, ramMemoryCard3, ramMemoryCard4))
                    .build();

            var laptop6 = Laptop.builder()
                    .modelName("toshiba")
                    .availableQty(6)
                    .price(new BigDecimal(6000))
                    .compatibleRams(Set.of(ramMemoryCard2))
                    .build();

            laptopRepository.saveAll(List.of(laptop1, laptop2, laptop3, laptop4, laptop5, laptop6));
            log.info("laptops saved !");
        };
    }
}
