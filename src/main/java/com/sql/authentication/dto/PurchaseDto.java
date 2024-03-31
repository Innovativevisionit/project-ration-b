package com.sql.authentication.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PurchaseDto {
    @NotBlank(message = "Smart Card Id is required")
    private String smartId;
    // @NotBlank(message = "Product is required")
    private String product;
    @NotNull(message = "Kg is required")
    private BigDecimal kg;
    @NotNull(message = "Amount is required")
    private BigDecimal amount;

}
