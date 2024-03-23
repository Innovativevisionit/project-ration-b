package com.sql.authentication.serviceimplementation.masters;

import com.sql.authentication.exception.AlreadyExistsException;
import com.sql.authentication.exception.NotFoundException;
import com.sql.authentication.model.Location;
import com.sql.authentication.repository.LocationRepository;
import com.sql.authentication.service.masters.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class LocationServiceImpl implements LocationService {
    @Autowired
    private LocationRepository locationRepository;
    public Location add(Location data){
        if(locationRepository.existsByName(data.getName())){
            throw new AlreadyExistsException("Name already exists");
        }
        locationRepository.save(data);
        return data;

    }
    public List<Location> list(){
        return locationRepository.findAll();
    }
    public List<Location> activeList(){
        return locationRepository.findAllByStatus(1);
    }
    public Location edit(int id){
        return locationRepository.findById(id).orElseThrow(()->new NotFoundException(id + "is not found"));
    }
    public Location update(Location data){
        Location Location= locationRepository.findById(data.getId())
                .orElseThrow(()->new NotFoundException(data.getId() + "is not found"));
        Location.setName(data.getName());
        locationRepository.save(Location);
        return Location;
    }

}
