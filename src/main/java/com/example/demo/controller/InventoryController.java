package com.example.demo.controller;

import java.util.Optional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.constant.UrlConst;
import com.example.demo.entity.UserInfo;
import com.example.demo.repository.UserInfoRepository;
import com.example.demo.service.InventoryService;

import lombok.RequiredArgsConstructor;

/*
 * 在庫管理画面のコントローラー。
 */
@Controller
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService service;
    private final UserInfoRepository userInfoRepository;

    /**
     * トップページと在庫一覧表示。
     */
    @GetMapping(UrlConst.INVENTORY)
    public String view(
            @RequestParam(required = false, defaultValue = "") String keyword,
            Model model) {
        model.addAttribute("products", service.search(keyword));
        model.addAttribute("keyword", keyword);
        model.addAttribute("inventoryUpdateDisabled", isInventoryUpdateDisabled());
        return "inventory";
    }

    /**
     * 在庫更新/在庫一覧表示。商品名が存在しない場合は新規登録、存在する場合は在庫数を増減。
     */
    @PostMapping(UrlConst.INVENTORYUPDATE)
    public String update(
            @RequestParam String productName,
            @RequestParam int quantity,
            @RequestParam(required = false, defaultValue = "") String keyword,
            Model model) {

        if (isInventoryUpdateDisabled()) {
            model.addAttribute("products", service.search(keyword));
            model.addAttribute("keyword", keyword);
            model.addAttribute("inventoryUpdateDisabled", true);
            return "inventory";
        }

        try {
            service.updateInventory(productName, quantity);
            model.addAttribute("message", "更新成功");
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
        }

        model.addAttribute("products", service.search(keyword));
        model.addAttribute("keyword", keyword);
        model.addAttribute("inventoryUpdateDisabled", false);

        return "inventory";
    }

    /**
     * 在庫更新が無効かどうかを判定。
     *
     * @return 在庫更新が無効な場合true、それ以外はfalse
     */
    private boolean isInventoryUpdateDisabled() {
        String loginId = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<UserInfo> userInfoOpt = userInfoRepository.findById(loginId);
        if (userInfoOpt.isEmpty()) {
            return false;
        }

        String roleName = userInfoOpt.get().getRoleName();
        return "USER".equalsIgnoreCase(roleName);
    }
}