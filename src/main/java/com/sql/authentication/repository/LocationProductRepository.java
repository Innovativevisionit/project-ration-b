package com.sql.authentication.repository;

import com.sql.authentication.model.Location;
import com.sql.authentication.model.LocationProduct;
import com.sql.authentication.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocationProductRepository extends JpaRepository<LocationProduct,Integer> {
    boolean existsByProductAndLocation(Product product, Location location);
    List<LocationProduct> findByLocation(Location location);
    LocationProduct findByLocationAndProduct(Product product, Location location);
}
