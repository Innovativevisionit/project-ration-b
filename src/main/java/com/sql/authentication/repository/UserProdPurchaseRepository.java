package com.sql.authentication.repository;

import com.sql.authentication.model.UserProdPurchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProdPurchaseRepository extends JpaRepository<UserProdPurchase,Integer> {
}
