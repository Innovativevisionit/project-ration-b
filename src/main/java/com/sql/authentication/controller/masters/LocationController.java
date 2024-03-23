package com.sql.authentication.controller.masters;

import com.sql.authentication.exception.AlreadyExistsException;
import com.sql.authentication.exception.NotFoundException;
import com.sql.authentication.model.Location;
import com.sql.authentication.payload.response.ErrorResponse;
import com.sql.authentication.payload.response.Response;
import com.sql.authentication.service.masters.LocationService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("api/location")
public class LocationController {
    @Autowired
    private LocationService locationService;
    @PostMapping("/store")
    public ResponseEntity<?> store(@Valid @RequestBody Location location){
        try{
            Location result= locationService.add(location);
            Response<Location> response=new Response<>(HttpStatus.CREATED.value(),"Success",result);
            return ResponseEntity.ok().body(response);
        } catch (AlreadyExistsException e) {
            ErrorResponse response = new ErrorResponse(HttpStatus.CONFLICT.value(), e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        } catch (Exception e){
            ErrorResponse response = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }
    @GetMapping("/list")
    public ResponseEntity<?> get(){
        try {
            List<Location> Locations=locationService.list();
            Response<List<Location>> response=new Response<>(HttpStatus.OK.value(),"Success",Locations);
            return ResponseEntity.ok().body(response);
        }catch (Exception e){
            ErrorResponse response = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }

    }

    @GetMapping("/activeList")
    public ResponseEntity<?> activeList(){
        try {
            List<Location> Locations=locationService.activeList();
            Response<List<Location>> response=new Response<>(HttpStatus.OK.value(),"Success",Locations);
            return ResponseEntity.ok().body(response);
        }catch (Exception e){
            ErrorResponse response = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }
    @GetMapping("/edit")
    public ResponseEntity<?> edit(@RequestParam @NotNull Integer id){
        try {
            Location Locations=locationService.edit(id);
            Response<Location> response=new Response<>(HttpStatus.OK.value(),"Success",Locations);
            return ResponseEntity.ok().body(response);
        }catch (NotFoundException e) {
            ErrorResponse response = new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }catch (Exception e){
            ErrorResponse response = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }
    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody  Location data){
        try {
            Location Locations=locationService.update(data);
            Response<Location> response=new Response<>(HttpStatus.OK.value(),"Success",Locations);
            return ResponseEntity.ok().body(response);
        }catch (NotFoundException e) {
            ErrorResponse response = new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }catch (Exception e){
            ErrorResponse response = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }
}
