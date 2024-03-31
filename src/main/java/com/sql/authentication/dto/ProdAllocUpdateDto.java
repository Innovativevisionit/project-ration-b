package com.sql.authentication.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
@Data
public class ProdAllocUpdateDto {
    @NotNull(message = "Id is required")
    private int id;
    private BigDecimal allocKg;
}
