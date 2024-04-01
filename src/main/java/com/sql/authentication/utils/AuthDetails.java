package com.sql.authentication.utils;

import org.springframework.stereotype.Service;

import com.sql.authentication.serviceimplementation.auth.UserDetailsImpl;
import jakarta.servlet.http.HttpSession;

@Service
public class AuthDetails {
    public UserDetailsImpl getUserDetails(HttpSession session) {

        UserDetailsImpl userDetails = (UserDetailsImpl) session.getAttribute("user");
        System.out.println("hello "+userDetails);

        if (userDetails != null) {
            return userDetails;
        } else {
            throw new RuntimeException("User not found in session");
        }
    }

    public String getEmail(HttpSession session) {

        Object emailObj = session.getAttribute("email");
        System.out.println("1"+session.getAttribute("email"));
        if (emailObj != null) {
            System.out.println("3"+emailObj.toString());
            return emailObj.toString();
        } else {
            throw new RuntimeException("User email not found in session");
        }
    }
} 
