package com.sql.authentication.controller.process;

import com.sql.authentication.dto.TokenDto;
import com.sql.authentication.service.process.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

}
