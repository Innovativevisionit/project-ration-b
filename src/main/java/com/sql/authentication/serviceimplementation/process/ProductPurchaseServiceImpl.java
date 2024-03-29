package com.sql.authentication.serviceimplementation.process;

import com.sql.authentication.dto.PurchaseDto;
import com.sql.authentication.exception.NotFoundException;
import com.sql.authentication.model.LocationProduct;
import com.sql.authentication.model.Product;
import com.sql.authentication.model.User;
import com.sql.authentication.model.UserProdPurchase;
import com.sql.authentication.repository.LocationProductRepository;
import com.sql.authentication.repository.ProductRepository;
import com.sql.authentication.repository.UserRepository;
import com.sql.authentication.service.process.ProductPurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Optional;
@Service
public class ProductPurchaseServiceImpl implements ProductPurchaseService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private LocationProductRepository locationProductRepository;

    public UserProdPurchase store(PurchaseDto dto){
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
        System.out.println(yearMonth);
        return userProdPurchase;
    }
}
