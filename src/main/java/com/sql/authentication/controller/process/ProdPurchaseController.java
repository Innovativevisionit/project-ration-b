package com.sql.authentication.controller.process;

import com.sql.authentication.dto.PurchaseDto;
import com.sql.authentication.model.UserProdPurchase;
import com.sql.authentication.service.process.ProductPurchaseService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public PurchaseDto prodPurchase(@Valid @RequestBody PurchaseDto dto){
        return productPurchaseService.store(dto);
    }
    
    @GetMapping("/list/{email}")
    public List<PurchaseDto>  prodPurchaseList(@PathVariable String email){
        return productPurchaseService.list(email);
    }

    @GetMapping("/purchaseList/{email}")
    public List<PurchaseDto>  prodUserPurchasedList(@PathVariable String email){
        return productPurchaseService.purchaseList(email);
    }

    @GetMapping("/empProductList/{email}")
    public List<PurchaseDto> empProductList(@PathVariable String email){
        return productPurchaseService.employeeUserPurchaseList(email);
    }
}

