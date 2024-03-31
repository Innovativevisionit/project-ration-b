package com.sql.authentication.serviceimplementation.process;

import com.sql.authentication.dto.IdDto;
import com.sql.authentication.dto.ProdAllocDto;
import com.sql.authentication.dto.ProdAllocUpdateDto;
import com.sql.authentication.dto.ProductRequestDto;
import com.sql.authentication.exception.AlreadyExistsException;
import com.sql.authentication.exception.NotFoundException;
import com.sql.authentication.model.*;
import com.sql.authentication.payload.response.ProductLocationList;
import com.sql.authentication.payload.response.ProductRequestList;
import com.sql.authentication.repository.*;
import com.sql.authentication.service.process.ProdAllocService;
import com.sql.authentication.serviceimplementation.auth.UserDetailsImpl;
import com.sql.authentication.utils.AuthDetails;
import jakarta.servlet.http.HttpSession;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ProdAllocServiceImpl implements ProdAllocService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private LocationProductRepository locationProductRepository;
    @Autowired
    private ProductRequestRepository productRequestRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuditorAware<String> auditorAware;
    @Autowired
    private AuthDetails authDetails;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public LocationProduct store(ProdAllocDto prodAllocDto){
        LocationProduct locationProduct=new LocationProduct();
        Product product=productRepository.findByName(prodAllocDto.getProduct())
                .orElseThrow(()-> new NotFoundException(prodAllocDto.getProduct()+" is not found"));
        Location location=locationRepository.findByName(prodAllocDto.getLocation())
                .orElseThrow(()-> new NotFoundException(prodAllocDto.getLocation()+" is not found"));
        if(locationProductRepository.existsByProductAndLocation(product,location)){
            throw new AlreadyExistsException("Already entry exists for this Product and Location");
        }
        locationProduct.setProduct(product);
        locationProduct.setLocation(location);
        locationProduct.setTotalKg(prodAllocDto.getAllocatedKg());
        locationProduct.setStockKg(prodAllocDto.getAllocatedKg());
        locationProductRepository.save(locationProduct);
        return locationProduct;
    }
    @Override
    public LocationProduct update(ProdAllocUpdateDto prodAllocDto){
        LocationProduct locationProduct=locationProductRepository.findById(prodAllocDto.getId()).orElseThrow(()->new NotFoundException(prodAllocDto.getId()+"Not Found"));
        locationProduct.setTotalKg(prodAllocDto.getAllocKg());
        locationProduct.setStockKg(prodAllocDto.getAllocKg());
        locationProductRepository.save(locationProduct);
        return locationProduct;
    }

    @Override
    public LocationProduct delete(IdDto prodAllocDto){
        LocationProduct locationProduct=locationProductRepository.findById(prodAllocDto.getId()).orElseThrow(()->new NotFoundException(prodAllocDto.getId()+"Not Found"));
        locationProductRepository.delete(locationProduct);
        return locationProduct;
    }
    // Location Wise Product Details
    @Override
    public List<ProductLocationList> locationProductList(HttpSession session){
        UserDetailsImpl userDetails=authDetails.getUserDetails(session);

        Optional<User> user=userRepository.findByEmail(userDetails.getEmail());
//        Location locationDetails=locationRepository.findByName(location)
//                .orElseThrow(()-> new NotFoundException(location+" is not found"));
        return locationProductRepository.findByLocation(user.get().getLocation()).stream().map(data->{
            return modelMapper.map(data,ProductLocationList.class);
        }).toList();
    }
    public List<LocationProduct> allLocationProduct(){
        return locationProductRepository.findAll();
    }

    public LocationProduct locationProduct(String loc,String prod){
        Product product=productRepository.findByName(prod)
                .orElseThrow(()-> new NotFoundException(prod+" is not found"));
        Location location=locationRepository.findByName(loc)
                .orElseThrow(()-> new NotFoundException(loc+" is not found"));
        LocationProduct locationProduct=locationProductRepository.findByLocationAndProduct(location,product);
        return locationProduct;
    }
    // Employee Raise the Request For Product when stock kg is insufficient
    public ProductRequest productRequest(ProductRequestDto requestDto, HttpSession session){
//        Optional<String> currentAuditor = auditorAware.getCurrentAuditor();
        UserDetailsImpl userDetails=authDetails.getUserDetails(session);

        Optional<User> user=userRepository.findByEmail(userDetails.getEmail());

        Product product=productRepository.findByName(requestDto.getProduct()).orElseThrow(()->new NotFoundException("Product is not Found"));
        ProductRequest productRequest=new ProductRequest();
        if(user.isPresent()){
            LocationProduct locationProduct=locationProductRepository.findByLocationAndProduct(user.get().getLocation(), product);
            if(locationProduct==null){
                throw  new NotFoundException("For this Product Location is not found");
            }
            Optional<ProductRequest> byLocationAndProduct=productRequestRepository
                    .findByLocationAndProductAndStatus(user.get().getLocation(), product,1);
            if(byLocationAndProduct.isPresent()){
                throw  new NotFoundException("Already Request Raised");
            }

            BigDecimal result = locationProduct.getTotalKg().divide(BigDecimal.valueOf(2));
            if(locationProduct.getStockKg().compareTo(result)>=0){
                System.out.println(result);
                productRequest.setLocation(user.get().getLocation());
                productRequest.setProduct(product);
                productRequest.setStockKg(locationProduct.getStockKg());
                productRequest.setUser(user.get());
                productRequest.setStatus(1);
                productRequestRepository.save(productRequest);
            }else {
                System.out.println(locationProduct.getStockKg().compareTo(result));
            }
        }
        return productRequest;

    }
    //Raised Request for Admin Side & Employee Side
    public List<ProductRequestList> productRequestListAdmin(String role, int status,HttpSession session){
        List<ProductRequest> productRequests = null;
        if(role.equalsIgnoreCase("Admin")) {
            productRequests = productRequestRepository.findByStatus(status);
        }else{
            UserDetailsImpl userDetails=authDetails.getUserDetails(session);
            Optional<User> user=userRepository.findByEmail(userDetails.getEmail());
            if(user.isPresent()){
                productRequests = productRequestRepository.findByLocationAndStatus(user.get().getLocation(),status);
            }
        }
        return productRequests.stream().map(data-> modelMapper.map(data,ProductRequestList.class)).toList();
    }
    //Request Accept
    @Override
    public ProductRequest productRequestAccept(int id){
        ProductRequest productRequest=productRequestRepository.findById(id).orElseThrow(()->new NotFoundException(id+"Not Found"));
        productRequest.setStatus(2);
        productRequestRepository.save(productRequest);
        LocationProduct locationProduct=locationProductRepository.findByLocationAndProduct(productRequest.getLocation(),productRequest.getProduct());
        locationProduct.setStockKg(locationProduct.getTotalKg());
        locationProductRepository.save(locationProduct);
        return productRequest;
    }

}
