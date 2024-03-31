package com.sql.authentication.serviceimplementation.process;

import com.sql.authentication.dto.TokenDto;
import com.sql.authentication.model.Token;
import com.sql.authentication.repository.TokenRepository;
import com.sql.authentication.repository.UserTokenRepository;
import com.sql.authentication.service.process.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TokenServiceImpl implements TokenService {
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private UserTokenRepository userTokenRepository;
    public Token store(TokenDto tokenDto){
        List<Token> tokenList=tokenRepository.findAll();
        Token token;
        if(!tokenList.isEmpty()){
            token=tokenList.stream().findFirst().orElse(new Token());
            token.setDate(tokenDto.getLocalDate());
            token.setStatus(1);
        }else{
            token=new Token();
            token.setDate(tokenDto.getLocalDate());
            token.setStatus(1);
        }
        return token;

    }

}
