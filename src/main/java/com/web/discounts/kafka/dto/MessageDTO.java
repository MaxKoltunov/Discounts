package com.web.discounts.kafka.dto;


import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageDTO {

    private Long discountId;

    private String name;

    private Timestamp startDate;

    private Timestamp endDate;
}
