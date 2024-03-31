package com.sql.authentication.repository;

import com.sql.authentication.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token,Integer> {

}
