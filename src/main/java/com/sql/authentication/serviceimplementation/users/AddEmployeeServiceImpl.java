package com.sql.authentication.serviceimplementation.users;

import com.sql.authentication.dto.UpdateUserDto;
import com.sql.authentication.exception.AlreadyExistsException;
import com.sql.authentication.exception.NotFoundException;
import com.sql.authentication.model.Location;
import com.sql.authentication.model.Role;
import com.sql.authentication.model.User;
import com.sql.authentication.payload.request.SignUpRequest;
import com.sql.authentication.payload.response.ApiResponse;
import com.sql.authentication.payload.response.EmpListRes;
import com.sql.authentication.payload.response.UserListRes;
import com.sql.authentication.repository.LocationRepository;
import com.sql.authentication.repository.RoleRepository;
import com.sql.authentication.repository.UserRepository;
import com.sql.authentication.service.users.AddEmployeeService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
@Service
public class AddEmployeeServiceImpl implements AddEmployeeService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
     private PasswordEncoder encoder;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private AuditorAware<String> auditorAware;
    @Autowired
    private ModelMapper modelMapper;

    public User addUser(SignUpRequest signUpRequest){
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
           throw new AlreadyExistsException("Username is already taken!");
        }
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new AlreadyExistsException("Email is already in use!");
        }
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode("123"));
        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName("Employee")
                    .orElseThrow(() -> new RuntimeException("Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                Role adminRole = roleRepository.findByName(role)
                        .orElseThrow(() -> new RuntimeException("Role is not found."));
                roles.add(adminRole);
            });
        }
        Location location=locationRepository.findByName(signUpRequest.getLocation())
                .orElseThrow(() -> new RuntimeException(signUpRequest.getLocation()+"is not found."));

        user.setRoles(roles);
        user.setLocation(location);
        user.setSmartId(signUpRequest.getSmartId());
        user.setAge(signUpRequest.getAge());
        user.setFamilyMembersCount(signUpRequest.getFamilyMembersCount());
        user.setSalary(signUpRequest.getSalary());
        userRepository.save(user);
        return user;
    }
    public User updateUser(UpdateUserDto data){
        User updateUser= userRepository.findById(data.getId())
                .orElseThrow(()->new NotFoundException(data.getId() + " Id not Found"));
        String userName = data.getUsername();
        String email= data.getEmail();
        if (!userName.equals(updateUser.getUsername())) {
            User existingUser = userRepository.findByUsername(userName).orElse(null);
            if (existingUser != null && !existingUser.getId().equals(data.getId())) {
                throw new AlreadyExistsException("Band name is already taken by another Band");
            }
        }
        if (!email.equals(updateUser.getEmail())) {
            User existingUser = userRepository.findByEmail(email).orElse(null);
            if (existingUser != null && !existingUser.getId().equals(data.getId())) {
                throw new AlreadyExistsException("Email is already taken by another user");
            }
        }
        Location location =locationRepository.findByName(data.getLocation())
                .orElseThrow(()->
                     new NotFoundException("Location is not found"));
        updateUser.setLocation(location);
        updateUser.setUsername(userName);
        updateUser.setEmail(data.getEmail());
        if(data.getRole().equalsIgnoreCase("User")){
            updateUser.setSmartId(data.getSmartId());
            updateUser.setAge(data.getAge());
            updateUser.setFamilyMembersCount(data.getFamilyMembersCount());
        }
        userRepository.save(updateUser);
        return updateUser;
    }
    public User deleteUser(int id){
        User updateUser= userRepository.findById(id)
                .orElseThrow(()->new NotFoundException(id + " Id not Found"));
        userRepository.deleteById(id);
        return updateUser;
    }


    public List<EmpListRes> employeeList(){
        Role adminRole = roleRepository.findByName("Employee")
                .orElseThrow(() -> new RuntimeException("Role is not found."));
         
        List<User> userList = userRepository.findByRoles(adminRole);

        return userList
                .stream()
               .map(list->{
                    return modelMapper.map(list, EmpListRes.class);
                }).toList();
    }

    public List<UserListRes> userList(){
        
        Role adminRole = roleRepository.findByName("User")
                .orElseThrow(() -> new RuntimeException("Role is not found."));
         
        List<User> userList = userRepository.findByRoles(adminRole);

        return userList
                .stream()
               .map(list->{
                    return modelMapper.map(list, UserListRes.class);
                }).toList();
    }

    @Override
    public List<UserListRes> empUserList() {
        
        // Optional<String> currentAuditor = auditorAware.getCurrentAuditor();

        Role userRole = roleRepository.findByName("User")
                .orElseThrow(() -> new RuntimeException("Role is not found."));
        // System.out.println(currentAuditor);
        User userList=userRepository.findByEmail("divya@gmail.com")
                .orElseThrow(() -> new RuntimeException("User is not found."));
        Location location=userList.getLocation();
        System.out.println(location);
        return userRepository.findByRolesAndLocation(userRole,location)
                .stream()
                .filter(data->!data.getUsername().equalsIgnoreCase(userList.getUsername())).map(list->{
                    return modelMapper.map(list,UserListRes.class);
                }).toList();
    }
   
}
