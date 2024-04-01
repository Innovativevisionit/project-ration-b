package com.sql.authentication.payload.response;

import lombok.Data;

@Data
public class UserListRes {
    
    private Long id;
    private String username;
    private String email;
    private String locationName;
    private String locationId;
    private String smartId;
    private String age;
    private String familyMembersCount;
}
