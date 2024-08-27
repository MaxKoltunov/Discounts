package com.web.discounts.service;


import com.web.discounts.entity.Discount;
import com.web.discounts.repository.DiscountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class DiscountService {
    private final DiscountRepository discountRepository;

    @Autowired
    public DiscountService(DiscountRepository discountRepository) {
        this.discountRepository = discountRepository;
    }

    @Cacheable(value = "discounts", key = "#type + #productType")
    public Discount addDiscount(String type, String productType) {
        Discount discount = new Discount();
        discount.setType(type);
        discount.setProductType(productType);
        log.info("A new discount type has been added");
        return discountRepository.save(discount);
    }

    @Cacheable(value = "discounts", key = "#type + #productType")
    public void deleteDiscountByType(String type, String productType) {
        discountRepository.deleteByType(type, productType);
        log.info("Discount template has been deleted");
    }
}
