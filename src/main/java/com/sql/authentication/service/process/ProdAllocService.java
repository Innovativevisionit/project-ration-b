package com.sql.authentication.service.process;

import com.sql.authentication.dto.ProdAllocDto;
import com.sql.authentication.model.LocationProduct;

public interface ProdAllocService {
    LocationProduct store(ProdAllocDto prodAllocDto);
}
