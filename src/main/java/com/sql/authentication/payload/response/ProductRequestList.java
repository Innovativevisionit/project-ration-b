package com.sql.authentication.payload.response;

import lombok.Data;

@Data
public class ProductRequestList {
    private int id;
    private String locationName;
    private String productName;
    private String userUsername;
    private String userEmail;
}
