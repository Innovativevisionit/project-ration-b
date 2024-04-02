package com.sql.authentication.controller.process;

import com.sql.authentication.dto.TokenDto;
import com.sql.authentication.service.process.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/token")
public class TokenController {
    @Autowired
    private TokenService tokenService;
    @PostMapping("/store")
    public ResponseEntity<?> store(@RequestBody TokenDto dto){
        try {
            return ResponseEntity.ok(tokenService.store(dto));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping("/claim/{email}")
    public ResponseEntity<?> storeClaim(@PathVariable String email){
        try {
            return ResponseEntity.ok(tokenService.storeClaim(email));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/getclaim/{email}")
    public ResponseEntity<?> getClaim(@PathVariable String email){
        try {
            return ResponseEntity.ok(tokenService.getClaim(email));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping("/claimDelivery/{email}")
    public ResponseEntity<?> storeDeliveryClaim(@PathVariable String email){
        try {
            return ResponseEntity.ok(tokenService.storeDeliveryClaim(email));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/getDeliveryClaim/{email}")
    public ResponseEntity<?> getDeliveryClaim(@PathVariable String email){
        try {
            return ResponseEntity.ok(tokenService.getDeliveryClaim(email));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

}
