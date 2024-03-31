package com.sql.authentication.service.process;

import com.sql.authentication.dto.TokenDto;
import com.sql.authentication.model.Token;

public interface TokenService {
    Token store(TokenDto tokenDto);
}
