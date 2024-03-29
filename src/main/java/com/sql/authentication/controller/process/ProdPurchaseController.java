package com.sql.authentication.controller.process;

import com.sql.authentication.dto.PurchaseDto;
import com.sql.authentication.model.UserProdPurchase;
import com.sql.authentication.service.process.ProductPurchaseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/purchase")
public class ProdPurchaseController {
    @Autowired
    private ProductPurchaseService productPurchaseService;
    @PostMapping("/store")
    public UserProdPurchase prodPurchase(@Valid @RequestBody PurchaseDto dto){
        return productPurchaseService.store(dto);
    }
}

