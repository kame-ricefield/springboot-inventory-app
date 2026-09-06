package com.example.demo.repository;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByProductName(String productName);

    // 商品名で在庫を部分一致検索します。
    List<Product> findByProductNameContainingIgnoreCaseAndQuantityGreaterThanOrderByIdAsc(
            String productName, int quantity);
}