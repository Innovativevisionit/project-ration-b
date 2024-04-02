package com.sql.authentication.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sql.authentication.model.Delivery;
import com.sql.authentication.model.Location;
import com.sql.authentication.model.LocationProduct;

public interface DeliveryReository extends JpaRepository<Delivery,Integer> {

    List<Delivery> findByLocation(Location location);
}
