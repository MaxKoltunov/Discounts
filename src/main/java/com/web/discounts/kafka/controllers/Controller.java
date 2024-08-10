package com.web.discounts.kafka.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.discounts.entity.Discount;
import com.web.discounts.kafka.dto.KafkaDiscountMessage;
import com.web.discounts.kafka.dto.MessageDTO;
import com.web.discounts.kafka.producers.ProducerService;
import com.web.discounts.repository.DiscountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.util.Optional;

@RestController
@RequestMapping("/api/discounts")
public class Controller {

    @Autowired
    private ProducerService producerService;

    @Autowired
    private DiscountRepository discountRepository;

    @PostMapping("/send")
    public String sendMesage(@RequestBody MessageDTO dto) {
        Optional<Discount> discountOpt = discountRepository.findById(dto.getDiscountId());
        if (discountOpt.isPresent()) {
            Discount discount = discountOpt.get();
            String name = dto.getName();
            String type = discount.getType();
            String productType = discount.getProductType();
            Date startDate = dto.getStartDate();
            Date endDate = dto.getEndDate();
            KafkaDiscountMessage kafkaDiscountMessage = new KafkaDiscountMessage();
            kafkaDiscountMessage.setName(name);
            kafkaDiscountMessage.setType(type);
            kafkaDiscountMessage.setProductType(productType);
            kafkaDiscountMessage.setStartDate(startDate);
            kafkaDiscountMessage.setEndDate(endDate);
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                String message = objectMapper.writeValueAsString(kafkaDiscountMessage);
                System.out.println(message);
                producerService.sendMessage("discount-topic", message);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            return "Message was sent";
        } else {
            return "There is no such discount template";
        }
    }

    // curl -X POST "http://localhost:8081/api/discounts/send" -H "Content-Type: application/json" -d "{\"discountId\": 1, \"name\": \"test_discount\", \"startDate\": \"2024-08-10\", \"endDate\": \"2024-08-14\"}"
}
