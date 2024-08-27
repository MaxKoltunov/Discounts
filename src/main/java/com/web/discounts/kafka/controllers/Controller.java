package com.web.discounts.kafka.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.discounts.entity.Discount;
import com.web.discounts.exceptions.NoDiscountTemplatesAvailableException;
import com.web.discounts.exceptions.ObjectNotFoundException;
import com.web.discounts.kafka.dto.KafkaDiscountMessage;
import com.web.discounts.kafka.dto.MessageDTO;
import com.web.discounts.kafka.producers.ProducerService;
import com.web.discounts.repository.DiscountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Slf4j
@RestController
@RequestMapping("/api/discounts")
public class Controller {

    @Autowired
    private ProducerService producerService;

    @Autowired
    private DiscountRepository discountRepository;


    private static final ZoneId TIME_ZONE = ZoneId.of("UTC+05:00");

    @PostMapping("/send")
    public void sendMesage(@RequestBody MessageDTO dto) {
        log.info("sendMessage() - starting");
        Optional<Discount> discountOpt = discountRepository.findById(dto.getDiscountId());
        if (discountOpt.isPresent()) {
            Discount discount = discountOpt.get();
            KafkaDiscountMessage kafkaDiscountMessage = KafkaDiscountMessage.builder()
                    .name(dto.getName())
                    .type(discount.getType())
                    .productType(discount.getProductType())
                    .startDate(dto.getStartDate())
                    .endDate(dto.getEndDate())
                    .build();
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                String message = objectMapper.writeValueAsString(kafkaDiscountMessage);
                System.out.println(message);
                producerService.sendMessage("discount-topic", message);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            log.info("Message was sent");
        } else {
            throw new ObjectNotFoundException("There is no such discount template");
        }
    }

    // curl -X POST "http://localhost:8081/api/discounts/send" -H "Content-Type: application/json" -d "{\"discountId\": 1, \"name\": \"test_discount\", \"startDate\": \"2024-08-10T06:00:00+05:00\", \"endDate\": \"2024-08-14T06:00:00+05:00\"}"

    @Scheduled(fixedRate = 30000)
    public void sendScheduledMessage() {
        log.info("sendScheduledMessage() - starting");
        List<Long> discountIds = discountRepository.selectAllId();

        List<String> types = discountRepository.selectAllType();

        List<String> productTypes = discountRepository.selectAllProductType();

        if (discountIds.isEmpty() || types.isEmpty() || productTypes.isEmpty()) {
            throw new NoDiscountTemplatesAvailableException("There are no discounts available");
        }

        Random random = new Random();

        Long randomId = discountIds.get(random.nextInt(discountIds.size()));
        String type = types.get(random.nextInt(types.size()));
        String productType = productTypes.get(random.nextInt(productTypes.size()));

        String name = "Discount " + type.toLowerCase() + " for " + productType.toLowerCase();

        ZonedDateTime now = ZonedDateTime.now(TIME_ZONE);
        Timestamp startDate = Timestamp.from(now.toInstant());
        long randomMillis = 60000 + random.nextInt(120000);

        Timestamp endDate = new Timestamp(startDate.getTime() + randomMillis);

        Optional<Discount> discountOpt = discountRepository.findById(randomId);

        if (discountOpt.isPresent()) {
            Discount discount = discountOpt.get();
            KafkaDiscountMessage kafkaDiscountMessage = KafkaDiscountMessage.builder()
                    .name(name)
                    .type(type)
                    .productType(productType)
                    .startDate(startDate)
                    .endDate(endDate)
                    .build();

            ObjectMapper objectMapper = new ObjectMapper();
            try {
                String message = objectMapper.writeValueAsString(kafkaDiscountMessage);
                System.out.println(message);
                producerService.sendMessage("discount-topic", message);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            log.info("Message was sent");
        } else {
            throw new ObjectNotFoundException("There is no such discount template");
        }
    }

}
