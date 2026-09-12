package com.example.demo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.nio.charset.StandardCharsets;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;

import com.example.demo.entity.Product;
import com.example.demo.repository.UserInfoRepository;
import com.example.demo.service.InventoryService;

class InventoryControllerCsvExportTest {

    @Test
    void exportCsv_shouldReturnAttachmentResponseWithCsvFileName() {
        InventoryService service = mock(InventoryService.class);
        UserInfoRepository userInfoRepository = mock(UserInfoRepository.class);
        InventoryController controller = new InventoryController(service, userInfoRepository);

        Product product = new Product();
        product.setId(1L);
        product.setProductName("テスト商品");
        product.setQuantity(10);

        when(service.search("")).thenReturn(List.of(product));
        when(service.createCsvContent(List.of(product))).thenReturn("ID,商品名,在庫数\n1,\"テスト商品\",10\n");
        Model model = new ConcurrentModel();

        ResponseEntity<byte[]> response = controller.exportCsv("", model);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("text/csv; charset=UTF-8", response.getHeaders().getFirst(HttpHeaders.CONTENT_TYPE));
        assertTrue(response.getHeaders().getFirst(HttpHeaders.CONTENT_DISPOSITION).contains("attachment"));
        assertTrue(
                response.getHeaders().getFirst(HttpHeaders.CONTENT_DISPOSITION).contains("filename=\"inventory.csv\""));
        assertTrue(new String(response.getBody(), StandardCharsets.UTF_8).startsWith("ID,商品名,在庫数\n"));
    }
}
