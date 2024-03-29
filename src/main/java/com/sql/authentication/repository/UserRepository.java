package com.sql.authentication.repository;

import com.sql.authentication.model.Location;
import com.sql.authentication.model.Role;
import com.sql.authentication.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    Optional<User> findBySmartId(String smartId);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    List<User> findByRoles(Role role);
    List<User> findByRolesAndLocation(Role role, Location location);
    List<User> findByLocation(Location location);
}
