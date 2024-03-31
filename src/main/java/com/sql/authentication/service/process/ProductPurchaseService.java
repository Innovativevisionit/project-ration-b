package com.sql.authentication.service.process;

import com.sql.authentication.dto.PurchaseDto;
import com.sql.authentication.model.UserProdPurchase;

public interface ProductPurchaseService {
    PurchaseDto store(PurchaseDto dto);
}
