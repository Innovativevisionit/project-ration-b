package com.sql.authentication.utils;

import com.sql.authentication.serviceimplementation.auth.UserDetailsImpl;
import jakarta.servlet.http.HttpSession;

public class AuthDetails {
    public UserDetailsImpl getUserDetails(HttpSession session) {
        UserDetailsImpl userDetails = (UserDetailsImpl) session.getAttribute("user");
        if (userDetails != null) {
            return userDetails;
        } else {
            throw new RuntimeException("User not found in session");
        }
    }
}
