package com.web.discounts.kafka.dto;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KafkaDiscountMessage {

    private String name;

    private String type;

    private String productType;

    private Timestamp startDate;

    private Timestamp endDate;
}
