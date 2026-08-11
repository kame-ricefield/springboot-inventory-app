package com.example.demo.service;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final ProductRepository repository;

    /**
     * 在庫更新処理
     * ・商品がなければ新規登録
     * ・あれば在庫数を増減
     */
    @Transactional
    public Product updateInventory(String productName, int changeQuantity) {

        Optional<Product> productOpt = repository.findByProductName(productName);

        // 新規登録
        if (productOpt.isEmpty()) {
            Product newProduct = new Product();
            newProduct.setProductName(productName);
            newProduct.setQuantity(changeQuantity);
            return repository.save(newProduct);
        }

        // 在庫更新
        Product product = productOpt.get();

        int newQuantity = product.getQuantity() + changeQuantity;

        if (newQuantity < 0) {
            throw new IllegalArgumentException("在庫数が0未満になるため更新できません。");
        }

        product.setQuantity(newQuantity);
        return repository.save(product);
    }

    /**
     * 在庫一覧取得（画面表示用）
     * 在庫数が0のものは画面に表示しない。
     */
    public List<Product> findAll() {
        return repository.findAll(Sort.by(Sort.Direction.ASC, "id")).stream()
                .filter(p -> p.getQuantity() > 0)
                .toList();
    }
}