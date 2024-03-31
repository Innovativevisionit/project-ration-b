package com.sql.authentication.payload.response;

import jakarta.persistence.Column;
import lombok.Data;

import java.math.BigDecimal;
@Data
public class ProductLocationList {
    private int id;
    private String locationName;
    private String locationId;
    private String productName;
    private String productId;
    private BigDecimal stockKg;
    private BigDecimal deliveredKg;
    private BigDecimal totalKg;
}
