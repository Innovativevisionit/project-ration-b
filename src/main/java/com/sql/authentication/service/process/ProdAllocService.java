package com.sql.authentication.service.process;

import com.sql.authentication.dto.IdDto;
import com.sql.authentication.dto.ProdAllocDto;
import com.sql.authentication.dto.ProdAllocUpdateDto;
import com.sql.authentication.dto.ProductRequestDto;
import com.sql.authentication.model.LocationProduct;
import com.sql.authentication.model.ProductRequest;
import com.sql.authentication.payload.response.ProductLocationList;
import com.sql.authentication.payload.response.ProductRequestList;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public interface ProdAllocService {
    LocationProduct store(ProdAllocDto prodAllocDto);
    LocationProduct update(ProdAllocUpdateDto prodAllocDto);
    LocationProduct delete(IdDto prodAllocDto);
    ProductRequest productRequest(ProductRequestDto requestDto, HttpSession session);
    List<ProductLocationList> locationProductList(HttpSession session);
    ProductRequest productRequestAccept(int id);
    List<ProductRequestList> productRequestListAdmin(String role, int status,HttpSession session);
}
