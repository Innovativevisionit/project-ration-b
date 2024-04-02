package com.sql.authentication.repository;

import com.sql.authentication.model.Location;
import com.sql.authentication.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface TokenRepository extends JpaRepository<Token,Integer> {

    List<Token> findByLocation(Location location);
}
