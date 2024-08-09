package com.web.discounts.controller;



import com.web.discounts.dto.DTO;
import com.web.discounts.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/discounts")
public class DiscountController {

    private final DiscountService discountService;

    @Autowired
    public DiscountController(DiscountService discountService) {
        this.discountService = discountService;
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello from discounts!";
    }


    @PostMapping("/add")
    public String addDiscount(@RequestBody DTO dto) {
        discountService.addDiscount(dto.getType(), dto.getProductType());
        return "A new discount type has been added";
    }
    // curl -X POST "http://localhost:8080/api/discounts/add" -H "Content-Type: application/json" -d "{\"type\":\"Seasonal\", \"productType\":\"bakery\"}"


    @DeleteMapping("/delete")
    public String deleteDiscount(@RequestBody DTO dto) {
        discountService.deleteDiscountByType(dto.getType(), dto.getProductType());
        return "Discount type has been deleted";
    }
    // curl -X DELETE "http://localhost:8080/api/discounts/delete" -H "Content-Type: application/json" -d "{\"type\":\"Seasonal\", \"productType\":\"bakery\"}"
}
