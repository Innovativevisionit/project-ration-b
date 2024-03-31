package com.sql.authentication.dto;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class ProdAllocDto {
    private String location;
    private String product;
    private BigDecimal allocatedKg;
    private BigDecimal deliveredKg;
    private BigDecimal stockKg;
}
