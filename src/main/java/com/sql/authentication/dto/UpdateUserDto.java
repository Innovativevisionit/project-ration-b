package com.sql.authentication.dto;

import com.sql.authentication.model.Location;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateUserDto {
    @NotBlank(message = "Id is required")
    private int id;
    @NotBlank(message = "User name is required")
    private String username;
    private String role;
    @NotBlank(message = "Email is required")
    private String email;
    @NotBlank(message = "Location is required")
    private String location;
    private String smartId;
    private String age;
    private String familyMembersCount;
}
