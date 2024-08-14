package com.web.discounts.repository;

import com.web.discounts.entity.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Repository
public interface DiscountRepository extends JpaRepository<Discount, Long> {

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM discountschema.discounts WHERE  type = :type AND productType = :productType", nativeQuery = true)
    void deleteByType(String type, String productType);

    @Query(value = "SELECT * FROM discountschema.discounts WHERE id = :id", nativeQuery = true)
    Optional<Discount> findById(Long id);

    @Query(value = "SELECT DISTINCT id FROM discountschema.discounts", nativeQuery = true)
    List<Long> selectAllId();

    @Query(value = "SELECT DISTINCT type FROM discountschema.discounts", nativeQuery = true)
    List<String> selectAllType();

    @Query(value = "SELECT DISTINCT product_type FROM discountschema.discounts", nativeQuery = true)
    List<String> selectAllProductType();
}
