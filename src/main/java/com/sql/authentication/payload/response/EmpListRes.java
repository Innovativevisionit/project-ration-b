package com.sql.authentication.payload.response;

import lombok.Data;

@Data
public class EmpListRes {
    private String username;
    private String email;
    private String locationName;
    private String locationId;
}
