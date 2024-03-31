package com.sql.authentication.repository;

import com.sql.authentication.model.ProductRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRequestRepository extends JpaRepository<ProductRequest,Integer> {
}
