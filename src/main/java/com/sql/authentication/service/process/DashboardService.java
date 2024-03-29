package com.sql.authentication.service.process;

import com.sql.authentication.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public interface DashboardService {
    Map<String,Long> countList();

}
