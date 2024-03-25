package com.sql.authentication.controller.masters;

import com.sql.authentication.exception.AlreadyExistsException;
import com.sql.authentication.exception.NotFoundException;
import com.sql.authentication.model.Product;
import com.sql.authentication.payload.response.ErrorResponse;
import com.sql.authentication.payload.response.Response;
import com.sql.authentication.service.masters.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("api/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @PostMapping("/store")
    public ResponseEntity<?> store(@Valid @RequestBody Product product){
        try{
            Product result= productService.add(product);
            Response<Product> response=new Response<>(HttpStatus.CREATED.value(),"Success",result);
            return ResponseEntity.ok().body(response);
        } catch (AlreadyExistsException e) {
            ErrorResponse response = new ErrorResponse(HttpStatus.CONFLICT.value(), e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        } catch (Exception e){
            ErrorResponse response = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }
    @GetMapping("/list")
    public ResponseEntity<?> get(){
        try {
            List<Product> products=productService.list();
            Response<List<Product>> response=new Response<>(HttpStatus.OK.value(),"Success",products);
            return ResponseEntity.ok().body(response);
        }catch (Exception e){
            ErrorResponse response = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }

    }

    @GetMapping("/activeList")
    public ResponseEntity<?> activeList(){
        try {
            List<Product> products=productService.activeList();
            Response<List<Product>> response=new Response<>(HttpStatus.OK.value(),"Success",products);
            return ResponseEntity.ok().body(response);
        }catch (Exception e){
            ErrorResponse response = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }
    
    @GetMapping("/edit")
    public ResponseEntity<?> edit(@RequestParam  @NotNull Integer id){
        try {
            Product products=productService.edit(id);
            Response<Product> response=new Response<>(HttpStatus.OK.value(),"Success",products);
            return ResponseEntity.ok().body(response);
        }catch (NotFoundException e) {
            ErrorResponse response = new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }catch (Exception e){
            ErrorResponse response = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }
    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody  Product data){
        try {
            Product products=productService.update(data);
            Response<Product> response=new Response<>(HttpStatus.OK.value(),"Success",products);
            return ResponseEntity.ok().body(response);
        }catch (NotFoundException e) {
            ErrorResponse response = new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }catch (Exception e){
            ErrorResponse response = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }
}
