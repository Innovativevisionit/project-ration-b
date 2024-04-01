package com.sql.authentication.serviceimplementation.process;

import com.sql.authentication.dto.PurchaseDto;
import com.sql.authentication.exception.NotFoundException;
import com.sql.authentication.model.LocationProduct;
import com.sql.authentication.model.Product;
import com.sql.authentication.model.User;
import com.sql.authentication.model.UserProdPurchase;
import com.sql.authentication.payload.response.UserListRes;
import com.sql.authentication.repository.LocationProductRepository;
import com.sql.authentication.repository.ProductRepository;
import com.sql.authentication.repository.UserProdPurchaseRepository;
import com.sql.authentication.repository.UserRepository;
import com.sql.authentication.service.process.ProductPurchaseService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;
@Service
public class ProductPurchaseServiceImpl implements ProductPurchaseService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private LocationProductRepository locationProductRepository;
    @Autowired
    private UserProdPurchaseRepository userProdPurchaseRepository;
    
    @Autowired
    private ModelMapper modelMapper;

    public PurchaseDto store(PurchaseDto dto){
        UserProdPurchase userProdPurchase=new UserProdPurchase();
        User user= userRepository.findBySmartId(dto.getSmartId()).orElseThrow(()->new NotFoundException(dto.getSmartId()+ "is not found"));
        Product product=productRepository.findByName(dto.getProduct()).orElseThrow(()->new NotFoundException(dto.getProduct()+"is not found"));
        LocationProduct locationProduct=locationProductRepository.findByLocationAndProduct(user.getLocation(),product);
        userProdPurchase.setUser(user);
        userProdPurchase.setProduct(product);
        userProdPurchase.setAmount(dto.getAmount());
        userProdPurchase.setKg(dto.getKg());
        LocalDate date=LocalDate.now();
        userProdPurchase.setPurchasedDate(date);
        YearMonth yearMonth=YearMonth.of(date.getYear(), date.getMonthValue());
        userProdPurchaseRepository.save(userProdPurchase);
        return dto;
    }

    @Override
    public List<PurchaseDto> list(String email) {
        List<UserProdPurchase> result = userProdPurchaseRepository.findAll();
        return result
                .stream()
               .map(list->{
                    return modelMapper.map(list, PurchaseDto.class);
                }).toList();
    }

    @Override
    public List<PurchaseDto> purchaseList(String email) {

        User userList=userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User is not found."));
        List<UserProdPurchase> result = userProdPurchaseRepository.findByUser(userList);
        return result
                .stream()
               .map(list->{
                    return modelMapper.map(list, PurchaseDto.class);
                }).toList();
    }
}
