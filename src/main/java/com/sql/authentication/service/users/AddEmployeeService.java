package com.sql.authentication.service.users;

import com.sql.authentication.dto.UpdateUserDto;
import com.sql.authentication.model.User;
import com.sql.authentication.payload.request.SignUpRequest;
import com.sql.authentication.payload.response.EmpListRes;
import com.sql.authentication.payload.response.UserListRes;

import jakarta.servlet.http.HttpSession;

import java.util.List;

public interface AddEmployeeService {
    User addUser(SignUpRequest signUpRequest);
    List<EmpListRes> employeeList();
    List<UserListRes> userList();
    User updateUser(UpdateUserDto data);
    User deleteUser(int id);
    List<UserListRes> empUserList(String email);
    List<String> smartCardList(String email);

}
