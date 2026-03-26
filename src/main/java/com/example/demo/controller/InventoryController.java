package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.constant.UrlConst;
import com.example.demo.service.InventoryService;

import lombok.RequiredArgsConstructor;

/*
 * 在庫管理画面のコントローラーです。
 */
@Controller
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService service;

    /**
     * 	トップページと在庫一覧を表示します。
     */
    @GetMapping(UrlConst.INVENTORY)
    public String view(Model model) {
        model.addAttribute("products", service.findAll());
        return "inventory";
    }

    /**
     * 在庫更新処理
     */
    @PostMapping(UrlConst.INVENTORYUPDATE)
    public String update(
            @RequestParam String productName,
            @RequestParam int quantity,
            Model model) {

        try {
            service.updateInventory(productName, quantity);
            model.addAttribute("message", "更新成功");
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
        }

        model.addAttribute("products", service.findAll());

        return "inventory";
    }
}