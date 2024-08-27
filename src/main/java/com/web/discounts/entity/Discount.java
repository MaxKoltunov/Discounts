package com.web.discounts.entity;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "discounts", schema = "discountschema")
public class Discount {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "discounts_seq_gen")
    @SequenceGenerator(name = "discounts_seq_gen", sequenceName = "discountschema.discounts_id_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String productType;
}
