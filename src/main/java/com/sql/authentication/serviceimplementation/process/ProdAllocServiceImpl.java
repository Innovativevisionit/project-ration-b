package com.sql.authentication.serviceimplementation.process;

import com.sql.authentication.dto.ProdAllocDto;
import com.sql.authentication.dto.ProductRequestDto;
import com.sql.authentication.exception.AlreadyExistsException;
import com.sql.authentication.exception.NotFoundException;
import com.sql.authentication.model.*;
import com.sql.authentication.payload.response.ProductLocationList;
import com.sql.authentication.repository.*;
import com.sql.authentication.service.process.ProdAllocService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
    private ModelMapper modelMapper;
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
        locationProductRepository.save(locationProduct);
        return locationProduct;
    }
    // Location Wise Product Details
    public List<ProductLocationList> locationProductList(String location){
        Location locationDetails=locationRepository.findByName(location)
                .orElseThrow(()-> new NotFoundException(location+" is not found"));
        return locationProductRepository.findByLocation(locationDetails).stream().map(data->{
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
    public ProductRequest productRequest(ProductRequestDto requestDto){
        Optional<String> currentAuditor = auditorAware.getCurrentAuditor();
        Optional<User> user=userRepository.findByEmail(currentAuditor.get());
        Product product=productRepository.findByName(requestDto.getProduct()).orElseThrow(()->new NotFoundException("Product is not Found"));
        ProductRequest productRequest=new ProductRequest();
        if(user.isPresent()){
            LocationProduct locationProduct=locationProductRepository.findByLocationAndProduct(user.get().getLocation(), product);
            if(locationProduct==null){
                throw  new NotFoundException("For this Product Location is not found");
            }
            BigDecimal result = locationProduct.getTotalKg().divide(BigDecimal.valueOf(2), 10);
            productRequest.setLocation(user.get().getLocation());
            productRequest.setProduct(product);
            productRequest.setStockKg(locationProduct.getStockKg());
            productRequest.setUser(user.get());
            productRequestRepository.save(productRequest);
        }
        return productRequest;

    }
}
