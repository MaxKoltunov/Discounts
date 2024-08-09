package com.web.discounts.entity;


import jakarta.persistence.*;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }
}
