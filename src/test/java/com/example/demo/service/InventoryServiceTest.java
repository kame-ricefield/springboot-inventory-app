package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
class InventoryServiceTest {

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private InventoryService service;

    @Test
    void updateInventory_shouldIncreaseExistingProductQuantity() {
        Product product = new Product();
        product.setProductName("ノートパソコン");
        product.setQuantity(5);

        when(repository.findByProductName("ノートパソコン")).thenReturn(Optional.of(product));
        when(repository.save(product)).thenReturn(product);

        Product testUpdated = service.updateInventory("ノートパソコン", 3);

        assertEquals(8, testUpdated.getQuantity());
        verify(repository).save(product);
    }

    @Test
    void updateInventory_shouldCreateNewProductWhenNotFound() {
        when(repository.findByProductName("マウス")).thenReturn(Optional.empty());
        when(repository.save(org.mockito.ArgumentMatchers.any(Product.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Product testCreated = service.updateInventory("マウス", 4);

        assertEquals("マウス", testCreated.getProductName());
        assertEquals(4, testCreated.getQuantity());
    }

    @Test
    void updateInventory_shouldRejectNegativeQuantity() {
        Product product = new Product();
        product.setProductName("ディスプレイ");
        product.setQuantity(2);

        when(repository.findByProductName("ディスプレイ")).thenReturn(Optional.of(product));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> service.updateInventory("ディスプレイ", -3));

        assertEquals("在庫数が0未満になるため更新できません。", exception.getMessage());
        verify(repository, never()).save(product);
    }
}
