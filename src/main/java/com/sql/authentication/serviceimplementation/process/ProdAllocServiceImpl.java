package com.sql.authentication.serviceimplementation.process;

import com.sql.authentication.dto.ProdAllocDto;
import com.sql.authentication.exception.AlreadyExistsException;
import com.sql.authentication.exception.NotFoundException;
import com.sql.authentication.model.Location;
import com.sql.authentication.model.LocationProduct;
import com.sql.authentication.model.Product;
import com.sql.authentication.repository.LocationProductRepository;
import com.sql.authentication.repository.LocationRepository;
import com.sql.authentication.repository.ProductRepository;
import com.sql.authentication.service.process.ProdAllocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdAllocServiceImpl implements ProdAllocService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private LocationProductRepository locationProductRepository;
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
    public List<LocationProduct> locationProductList(String location){
        Location locationDetails=locationRepository.findByName(location)
                .orElseThrow(()-> new NotFoundException(location+" is not found"));
        return locationProductRepository.findByLocation(locationDetails);
    }
    public List<LocationProduct> allLocationProduct(){
        return locationProductRepository.findAll();
    }
    public LocationProduct locationProduct(String loc,String prod){
        Product product=productRepository.findByName(prod)
                .orElseThrow(()-> new NotFoundException(prod+" is not found"));
        Location location=locationRepository.findByName(loc)
                .orElseThrow(()-> new NotFoundException(loc+" is not found"));

        LocationProduct locationProduct=locationProductRepository.findByLocationAndProduct(product,location);
        return locationProduct;
    }
}
