package com.sql.authentication.service.process;

import com.sql.authentication.dto.ProdAllocDto;
import com.sql.authentication.dto.ProductRequestDto;
import com.sql.authentication.model.LocationProduct;
import com.sql.authentication.model.ProductRequest;
import com.sql.authentication.payload.response.ProductLocationList;

import java.util.List;

public interface ProdAllocService {
    LocationProduct store(ProdAllocDto prodAllocDto);
    ProductRequest productRequest(ProductRequestDto requestDto);
    List<ProductLocationList> locationProductList(String location);
}
