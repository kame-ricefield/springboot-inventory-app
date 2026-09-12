package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
class InventoryServiceCsvTest {

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private InventoryService service;

    @Test
    void createCsvContent_shouldIncludeHeaderAndProducts() {
        Product product1 = new Product();
        product1.setId(1L);
        product1.setProductName("ノートパソコン");
        product1.setQuantity(10);

        Product product2 = new Product();
        product2.setId(2L);
        product2.setProductName("マウス 高性能");
        product2.setQuantity(5);

        String csv = service.createCsvContent(List.of(product1, product2));

        assertEquals(
                "ID,商品名,在庫数\n1,ノートパソコン,10\n2,マウス 高性能,5\n",
                csv);
    }
}
