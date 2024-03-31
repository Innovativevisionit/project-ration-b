package com.sql.authentication.controller;

import com.sql.authentication.dto.ProdAllocDto;
import com.sql.authentication.dto.ProductRequestDto;
import com.sql.authentication.exception.AlreadyExistsException;
import com.sql.authentication.exception.NotFoundException;
import com.sql.authentication.model.LocationProduct;
import com.sql.authentication.model.ProductRequest;
import com.sql.authentication.payload.response.ErrorResponse;
import com.sql.authentication.service.process.ProdAllocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/prodAlloc")
public class ProdAllocController {
    @Autowired
    private ProdAllocService prodAllocService;
    
    @PostMapping("/store")
    public Object store(@RequestBody ProdAllocDto dto){
        try{
            LocationProduct locationProduct=prodAllocService.store(dto);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(locationProduct);
        } catch (AlreadyExistsException e) {
            ErrorResponse response = new ErrorResponse(HttpStatus.CONFLICT.value(), e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }
        catch (NotFoundException e) {
            ErrorResponse response = new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }catch (Exception e){
            ErrorResponse response = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }
    @PostMapping("/productRequest")
    public Object productRequest(@RequestBody ProductRequestDto dto){
        try{
            ProductRequest locationProduct=prodAllocService.productRequest(dto);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(locationProduct);
        } catch (AlreadyExistsException e) {
            ErrorResponse response = new ErrorResponse(HttpStatus.CONFLICT.value(), e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }
        catch (NotFoundException e) {
            ErrorResponse response = new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }catch (Exception e){
            ErrorResponse response = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }
}
