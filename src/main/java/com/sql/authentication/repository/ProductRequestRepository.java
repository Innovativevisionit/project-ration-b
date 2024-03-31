package com.sql.authentication.repository;

import com.sql.authentication.model.Location;
import com.sql.authentication.model.Product;
import com.sql.authentication.model.ProductRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRequestRepository extends JpaRepository<ProductRequest,Integer> {
    Optional<ProductRequest> findByLocationAndProductAndStatus(Location location, Product product, int status);
    List<ProductRequest> findByStatus(int status);
    List<ProductRequest> findByLocationAndStatus(Location location,int status);
}
