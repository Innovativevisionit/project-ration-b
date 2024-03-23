package com.sql.authentication.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
public class LocationProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

//    @OneToOne
//    @JoinColumn(name = "location_id")
//    private Location location;
//    @OneToOne
//    @JoinColumn(name = "user_id")
//    private User user;
//    private
//    List<ProductDetails> productDetailsList;
//    @Data
//    public static class ProductDetails {
//        private String name;
//        private String allocated;
//        private String existing;
//        private String total;
//    }
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @Column(name = "stock_kg")
    private BigDecimal stockKg;
    @Column(name = "delivered_kg")
    private BigDecimal deliveredKg;

    @Column(name = "total_kg")
    private BigDecimal totalKg;

}
