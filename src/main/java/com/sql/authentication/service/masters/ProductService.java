package com.sql.authentication.service.masters;

import com.sql.authentication.model.Product;

import java.util.List;

public interface ProductService {
    Product add(Product data);
    List<Product> list();
    List<Product> activeList();
    Product edit(int id);
}
