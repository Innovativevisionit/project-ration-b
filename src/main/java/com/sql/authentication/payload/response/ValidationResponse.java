package com.sql.authentication.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidationResponse {
    private int statusCode;
    private String message;
    private Map<String, String> errors;
}
