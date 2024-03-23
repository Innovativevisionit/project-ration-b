package com.sql.authentication.service.masters;

import com.sql.authentication.model.Location;
import com.sql.authentication.model.Product;

import java.util.List;

public interface LocationService {
    Location add(Location data);
    List<Location> list();
    List<Location> activeList();
    Location edit(int id);
    Location update(Location data);
}
