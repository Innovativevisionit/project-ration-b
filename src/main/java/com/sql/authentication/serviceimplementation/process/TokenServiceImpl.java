package com.sql.authentication.serviceimplementation.process;

import com.sql.authentication.dto.TokenDto;
import com.sql.authentication.model.Token;
import com.sql.authentication.model.User;
import com.sql.authentication.repository.TokenRepository;
import com.sql.authentication.repository.UserRepository;
import com.sql.authentication.repository.UserTokenRepository;
import com.sql.authentication.service.process.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserTokenRepository userTokenRepository;

    @Override
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

    @Override
    public String storeClaim(String email) {

        LocalDate date = LocalDate.now();
        Token token = new Token();
        Optional<User> user=userRepository.findByEmail(email);
        token.setEmail(email);
        token.setLocation(user.get().getLocation());
        token.setDate(date);
        tokenRepository.save(token);
        return "success";
    }

}
