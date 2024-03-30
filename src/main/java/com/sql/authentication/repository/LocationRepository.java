package com.sql.authentication.repository;

import com.sql.authentication.model.Location;
import com.sql.authentication.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location,Integer> {
    boolean existsByName(String name);
    Optional<Location> findByName(String name);
    List<Location> findAllByStatus(int i);
    long countByStatus(int status);

}
