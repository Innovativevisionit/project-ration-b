package com.sql.authentication.repository;

import com.sql.authentication.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Integer> {
    boolean existsByName(String name);
    Optional<Product> findByName(String name);
    List<Product> findAllByStatus(int i);
    long countByStatus(int status);
}
