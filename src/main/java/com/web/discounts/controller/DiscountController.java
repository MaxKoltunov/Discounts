package com.web.discounts.controller;



import com.web.discounts.dto.DTO;
import com.web.discounts.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/discounts")
public class DiscountController {

    @Autowired
    private DiscountService discountService;


    @PostMapping("/add")
    public void addDiscount(@RequestBody DTO dto) {
        discountService.addDiscount(dto.getType(), dto.getProductType());
    }
    // curl -X POST "http://localhost:8081/api/discounts/add" -H "Content-Type: application/json" -d "{\"type\":\"Seasonal\", \"productType\":\"bakery\"}"


    @DeleteMapping("/delete")
    public void deleteDiscount(@RequestBody DTO dto) {
        discountService.deleteDiscountByType(dto.getType(), dto.getProductType());
    }
    // curl -X DELETE "http://localhost:8081/api/discounts/delete" -H "Content-Type: application/json" -d "{\"type\":\"Seasonal\", \"productType\":\"bakery\"}"
}
