package com.sql.authentication.service.process;

import java.util.List;

import com.sql.authentication.dto.TokenDto;
import com.sql.authentication.model.Delivery;
import com.sql.authentication.model.Token;
import com.sql.authentication.payload.response.RequestResponse;

public interface TokenService {

    Token store(TokenDto tokenDto);
    String storeClaim(String email);
    String storeDeliveryClaim(String email);
    List<RequestResponse> getClaim(String email);
    List<RequestResponse> getDeliveryClaim(String email);
    
}
