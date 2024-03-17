package com.sql.authentication.serviceimplementation.masters;

import com.sql.authentication.exception.AlreadyExistsException;
import com.sql.authentication.exception.NotFoundException;
import com.sql.authentication.model.Product;
import com.sql.authentication.repository.ProductRepository;
import com.sql.authentication.service.masters.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    public Product add(Product data){

        if(productRepository.existsByName(data.getName())){
            throw new AlreadyExistsException("Name already exists");
        }
        productRepository.save(data);
        return data;

    }
    public List<Product> list(){
        return productRepository.findAll();
    }
    public List<Product> activeList(){
        return productRepository.findAllByStatus(1);
    }
    public Product edit(int id){
        return productRepository.findById(id).orElseThrow(()->new NotFoundException(id + "is not found"));
    }

}
