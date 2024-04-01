package com.sql.authentication.repository;

import com.sql.authentication.model.User;
import com.sql.authentication.model.UserProdPurchase;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProdPurchaseRepository extends JpaRepository<UserProdPurchase,Integer> {

    List<UserProdPurchase> findByUser(User user);
}
