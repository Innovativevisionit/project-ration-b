package com.sql.authentication.controller.users;

import com.sql.authentication.dto.UpdateUserDto;
import com.sql.authentication.exception.AlreadyExistsException;
import com.sql.authentication.model.User;
import com.sql.authentication.payload.request.SignUpRequest;
import com.sql.authentication.payload.response.EmpListRes;
import com.sql.authentication.payload.response.ErrorResponse;
import com.sql.authentication.payload.response.UserListRes;
import com.sql.authentication.service.users.AddEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("api/users")
public class AddEmployeeController {
    @Autowired
    private AddEmployeeService addEmployeeService;
    @PostMapping("/addUser")
    public ResponseEntity<?> addUser(@RequestBody SignUpRequest signUpRequest){
        try{
            Set<String> role=new HashSet<>();
            role.add("User");
            signUpRequest.setRole(role);
            User user=addEmployeeService.addUser(signUpRequest);
            return ResponseEntity.ok().body(user);
        }catch (AlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

    }
    @PostMapping("/addEmployee")
    public ResponseEntity<?> addEmployee(@RequestBody SignUpRequest signUpRequest){
        try{
            Set<String> role=new HashSet<>();
            role.add("Employee");
            signUpRequest.setRole(role);
            User user=addEmployeeService.addUser(signUpRequest);
            return ResponseEntity.ok().body(user);
        }catch (AlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

    }
    @GetMapping("/employeeList")
    public ResponseEntity<?> employeeList(){
        try {
           List<EmpListRes> list= addEmployeeService.employeeList();
           return ResponseEntity.ok(list);
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/usersList")
    public ResponseEntity<?> userList(){
        try {
           List<UserListRes> list= addEmployeeService.userList();
           return ResponseEntity.ok(list);
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
    
    @GetMapping("/empUserList")
    public ResponseEntity<?> empUserList(){
        try {
            List<UserListRes> list= addEmployeeService.userList();
            return ResponseEntity.ok(list);
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
    @PostMapping("/updateUser")
    public ResponseEntity<?> updateUser(@RequestBody UpdateUserDto dto){
        try{
            dto.setRole("User");
            User user=addEmployeeService.updateUser(dto);
            return ResponseEntity.ok().body(user);
        }catch (AlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
    @PostMapping("/updateEmployee")
    public ResponseEntity<?> updateEmployee(@RequestBody UpdateUserDto dto){
        try{
            dto.setRole("Employee");
            User user=addEmployeeService.updateUser(dto);
            return ResponseEntity.ok().body(user);
        }catch (AlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam int id){
        try{
            User user=addEmployeeService.deleteUser(id);
            return ResponseEntity.ok(user);
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
