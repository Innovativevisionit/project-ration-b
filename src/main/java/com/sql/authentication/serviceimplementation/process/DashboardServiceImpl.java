package com.sql.authentication.serviceimplementation.process;

import com.sql.authentication.model.Role;
import com.sql.authentication.repository.LocationRepository;
import com.sql.authentication.repository.ProductRepository;
import com.sql.authentication.repository.RoleRepository;
import com.sql.authentication.repository.UserRepository;
import com.sql.authentication.service.RoleService;
import com.sql.authentication.service.process.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DashboardServiceImpl  implements DashboardService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;

    public Map<String,Long> countList(){

        Map<String,Long> count=new HashMap<>();
        Role empRole = roleRepository.findByName("Employee")
                .orElseThrow(() -> new RuntimeException("Role is not found."));
        Role userRole = roleRepository.findByName("User")
                .orElseThrow(() -> new RuntimeException("Role is not found."));
        count.put("activeProduct",productRepository.countByStatus(1));
        count.put("inactiveProduct",productRepository.countByStatus(0));
        count.put("activeLocation",locationRepository.countByStatus(1));
        count.put("inactiveLocation",locationRepository.countByStatus(0));
        count.put("employee",userRepository.countByRoles(empRole));
        count.put("user",userRepository.countByRoles(userRole));
        return count;

    }
}
