package com.sql.authentication.repository;

import com.sql.authentication.model.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTokenRepository extends JpaRepository<UserToken,Integer> {

}
