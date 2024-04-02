package com.sql.authentication.serviceimplementation.process;

import com.sql.authentication.dto.TokenDto;
import com.sql.authentication.model.Delivery;
import com.sql.authentication.model.Token;
import com.sql.authentication.model.User;
import com.sql.authentication.payload.response.RequestResponse;
import com.sql.authentication.repository.DeliveryReository;
import com.sql.authentication.repository.TokenRepository;
import com.sql.authentication.repository.UserRepository;
import com.sql.authentication.repository.UserTokenRepository;
import com.sql.authentication.service.process.TokenService;
import lombok.AllArgsConstructor;

import org.modelmapper.ModelMapper;
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
    private DeliveryReository deliveryRepository;
    
    @Autowired
    private UserTokenRepository userTokenRepository;

    @Autowired
    private ModelMapper modelMapper;

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
        token.setUser(user.get());
        token.setLocation(user.get().getLocation());
        token.setDate(date);
        tokenRepository.save(token);
        return "success";
    }

   

    @Override
    public String storeDeliveryClaim(String email) {
        LocalDate date = LocalDate.now();
        Delivery delivery = new Delivery();
        Optional<User> user=userRepository.findByEmail(email);
        delivery.setUser(user.get());
        delivery.setLocation(user.get().getLocation());
        delivery.setDate(date);
        deliveryRepository.save(delivery);
        return "success";
    }

    @Override
    public List<RequestResponse> getClaim(String email) {
        Optional<User> user=userRepository.findByEmail(email);
        List<RequestResponse> tokenList = tokenRepository.findByLocation(user.get().getLocation()).stream().map(data->modelMapper.map(data,RequestResponse.class)).toList();

        return tokenList;
    }
    @Override
    public List<RequestResponse> getDeliveryClaim(String email) {
        Optional<User> user=userRepository.findByEmail(email);
        List<RequestResponse> deliveryList = deliveryRepository.findByLocation(user.get().getLocation()).stream().map(data->modelMapper.map(data,RequestResponse.class)).toList();
        
        return deliveryList;
    }

}
