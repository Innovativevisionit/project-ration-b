package com.sql.authentication.payload.response;

import lombok.Data;

@Data
public class EmpListRes {

    private Long id;
    private String username;
    private String email;
    private String locationName;
    private String locationId;
    private String salary;
    private String age;
}
