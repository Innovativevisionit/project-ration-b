package com.sql.authentication.service.process;

import java.io.IOException;
import java.util.List;

import com.sql.authentication.dto.PurchaseDto;
import com.sql.authentication.model.UserProdPurchase;
import jakarta.servlet.http.HttpSession;

public interface ProductPurchaseService {
    PurchaseDto store(PurchaseDto dto) throws IOException;
    List<PurchaseDto> list(String email);
    List<PurchaseDto> purchaseList(String email);
    List<PurchaseDto> employeeUserPurchaseList(String email);
}
