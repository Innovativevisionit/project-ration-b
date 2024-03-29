package com.sql.authentication.serviceimplementation.users;

import com.sql.authentication.exception.AlreadyExistsException;
import com.sql.authentication.model.Location;
import com.sql.authentication.model.Role;
import com.sql.authentication.model.User;
import com.sql.authentication.payload.request.SignUpRequest;
import com.sql.authentication.payload.response.ApiResponse;
import com.sql.authentication.repository.LocationRepository;
import com.sql.authentication.repository.RoleRepository;
import com.sql.authentication.repository.UserRepository;
import com.sql.authentication.service.users.AddEmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        userRepository.save(user);
        return user;
    }

    public List<User> employeeList(){
        Role adminRole = roleRepository.findByName("Employee")
                .orElseThrow(() -> new RuntimeException("Role is not found."));
         
        List<User> userList = userRepository.findByRoles(adminRole);

        List<User> userResult = new ArrayList<>();

        for (User users : userList) {
            User user = new User();
            user.setUsername(users.getUsername());
            user.setEmail(users.getEmail());
            userResult.add(user);
        }
        return userResult;
    }

}
