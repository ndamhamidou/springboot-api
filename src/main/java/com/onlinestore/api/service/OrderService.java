package com.onlinestore.api.service;

import com.onlinestore.api.dto.*;
import com.onlinestore.api.entity.Laptop;
import com.onlinestore.api.entity.Order;
import com.onlinestore.api.entity.OrderLaptopItems;
import com.onlinestore.api.entity.RAM_MemoryCard;
import com.onlinestore.api.messaging.RabbitMQProducer;
import com.onlinestore.api.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final LaptopService laptopService;
    private final RAMService ramService;
    private final WebClient.Builder webClientBuilder;
    private final RabbitMQProducer producer;

    public String placeOrder(OrderRequest orderRequest){

        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLaptopItems> orderLaptopItems = orderRequest.getOrderLaptopItemsDtoList()
                .stream()
                .map(this::mapToDto)
                .toList();

        order.setOrderLaptopItems(orderLaptopItems);

        List<Inventory> laptopModelsQty = getOrderLaptopModelsQty(order.getOrderLaptopItems());
        List<InventoryResponse> inventoryResponses = laptopService.isAvailable(laptopModelsQty);

        boolean laptopsAvailable = inventoryResponses.stream()
                .allMatch(InventoryResponse::isAvailable);
        if(laptopsAvailable){
            orderRepository.save(order);
            laptopService.updateQuantities(laptopModelsQty);

            /***********************************
             * sending data to invoice service *
             * *********************************/
            InvoiceData invoiceData = new InvoiceData(orderRequest.getUserId(), order.getOrderNumber());
            webClientBuilder.build().get()
                    .uri("http://invoice-service/online-store/api/send-invoice",
                            uriBuilder -> uriBuilder.queryParam("data", invoiceData).build());


            /******************************
             * sending email notification *
             ******************************/
            List<LaptopRamCompatible> laptopRamCompatibles = laptopModelsQty.stream()
                    .map(this::getRamCompatibles)
                    .toList();

            String message = formatEmailText(laptopRamCompatibles);
            webClientBuilder.build().get()
                    .uri("http://email-service/online-store/api/send",
                            uriBuilder -> uriBuilder.queryParam("text", message).build());

            /*****************************
             * send message to Rabbit MQ *
             *****************************/
            // producer.sendMessage(message);

            return "Order placed successfully !";
        }
        else{
            throw new IllegalArgumentException("An error occurred, please try again later !");
        }
    }

    private String formatEmailText(List<LaptopRamCompatible> laptopRamCompatibles) {
        StringBuilder text = new StringBuilder();
        for (LaptopRamCompatible laptopRamCompatible : laptopRamCompatibles){
            text.append(laptopRamCompatible.getModelName()).append("\nCompatible RAM Memory cards : \n");
            for(RAM_MemoryCard ramMemoryCard : laptopRamCompatible.getCompatibleRams()){
                text.append(ramMemoryCard.getName()).append("\n");
            }
        }
        return text.toString();
    }

    private LaptopRamCompatible getRamCompatibles(Inventory inventory) {
        Optional<Laptop> laptop = laptopService.findByModelName(inventory.getModelName());
        List<RAM_MemoryCard> ramCompatible = ramService.findAllByLaptopsContaining(laptop.get());
        return new LaptopRamCompatible(laptop.get().getModelName(), ramCompatible);
    }

    private List<Inventory> getOrderLaptopModelsQty(List<OrderLaptopItems> orderLaptopItems) {
        return orderLaptopItems.stream()
                .map(item -> new Inventory(item.getModelName(), item.getQuantity()))
                .toList();
    }

    private OrderLaptopItems mapToDto(OrderLaptopItemsDto orderLaptopItemsDto){
        OrderLaptopItems orderLaptopItems = new OrderLaptopItems();
        orderLaptopItems.setPrice(orderLaptopItemsDto.getPrice());
        orderLaptopItems.setModelName(orderLaptopItemsDto.getModelName());
        orderLaptopItems.setQuantity(orderLaptopItemsDto.getQuantity());

        return orderLaptopItems;
    }
}
