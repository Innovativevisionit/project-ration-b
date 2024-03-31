package com.sql.authentication.service.process;

import com.sql.authentication.dto.ProdAllocDto;
import com.sql.authentication.dto.ProductRequestDto;
import com.sql.authentication.model.LocationProduct;
import com.sql.authentication.model.ProductRequest;

import java.util.List;

public interface ProdAllocService {
    LocationProduct store(ProdAllocDto prodAllocDto);
    ProductRequest productRequest(ProductRequestDto requestDto);
    List<LocationProduct> locationProductList(String location);
}
