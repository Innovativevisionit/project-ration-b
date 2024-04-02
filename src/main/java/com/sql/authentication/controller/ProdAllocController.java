package com.sql.authentication.controller;

import com.sql.authentication.dto.ProdAllocDto;
import com.sql.authentication.dto.ProdAllocUpdateDto;
import com.sql.authentication.dto.ProductRequestDto;
import com.sql.authentication.dto.IdDto;
import com.sql.authentication.exception.AlreadyExistsException;
import com.sql.authentication.exception.NotFoundException;
import com.sql.authentication.model.LocationProduct;
import com.sql.authentication.model.ProductRequest;
import com.sql.authentication.payload.response.ErrorResponse;
import com.sql.authentication.payload.response.ProductLocationList;
import com.sql.authentication.payload.response.ProductRequestList;
import com.sql.authentication.service.process.ProdAllocService;
import com.sql.authentication.utils.AuthDetails;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/prodAlloc")
public class ProdAllocController {
    @Autowired
    private ProdAllocService prodAllocService;
    @Autowired
    private AuthDetails authDetails;
    
    @PostMapping("/store")
    public ResponseEntity<?> store(@RequestBody ProdAllocDto dto){
        try{
            LocationProduct locationProduct=prodAllocService.store(dto);
            return ResponseEntity.status(HttpStatus.OK).body(locationProduct);
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
    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody @Valid ProdAllocUpdateDto dto){
        try{
            LocationProduct locationProduct=prodAllocService.update(dto);
            return ResponseEntity.status(HttpStatus.OK).body(locationProduct);
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
    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestBody IdDto dto){
        try{
            LocationProduct locationProduct=prodAllocService.delete(dto);
            return ResponseEntity.status(HttpStatus.OK).body(locationProduct);
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
    public ResponseEntity<?> productRequest(@RequestBody ProductRequestDto dto, HttpSession session){
        try{
            ProductRequest locationProduct=prodAllocService.productRequest(dto,session);
            return ResponseEntity.status(HttpStatus.OK).body(locationProduct);
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
    @PutMapping("/updateRequest")
    public ResponseEntity<?> updateRequest(@RequestBody IdDto data){
        try{
            ProductRequest locationProduct=prodAllocService.productRequestAccept(data.getId());
            return ResponseEntity.status(HttpStatus.OK).body(locationProduct);
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

    @GetMapping("/locationProductList/{email}")
    public List<ProductLocationList> locationProductList(@PathVariable String email){
        return prodAllocService.locationProductList(email);
    }

    @GetMapping("/locationProduct")
    public List<ProductLocationList> locationProduct(){
        return prodAllocService.locationProduct();
    }

    @GetMapping("/requestListAdmin")
    public List<ProductRequestList> productRequestList(@RequestParam int status,HttpSession session){
        return prodAllocService.productRequestListAdmin("Admin",status,session);
    }
    @GetMapping("/requestListEmp")
    public List<ProductRequestList> productRequestListEmp(@RequestParam int status,HttpSession session){
        return prodAllocService.productRequestListAdmin("Employee",status,session);
    }


}
