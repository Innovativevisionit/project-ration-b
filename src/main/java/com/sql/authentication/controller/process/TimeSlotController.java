package com.sql.authentication.controller.process;

import com.sql.authentication.dto.TimeSlotDto;
import com.sql.authentication.model.TimeSlot;
import com.sql.authentication.service.process.TimeSlotService;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/timeSlot")
public class TimeSlotController {
    @Autowired
    private TimeSlotService timeSlotService;
    @PostMapping("store")
    public ResponseEntity<?> store(@RequestBody TimeSlotDto dto){
        try {
            TimeSlot timeSlot=timeSlotService.store(dto);
            return ResponseEntity.ok(timeSlot);
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
    @GetMapping("today")
    public ResponseEntity<?> currentTimeSlot(){
        try {
            TimeSlot timeSlot=timeSlotService.currentTimeSlot();
            return ResponseEntity.ok(timeSlot);
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
    @GetMapping("list")
    public ResponseEntity<?> list(){
        try {
            List<TimeSlot> timeSlot=timeSlotService.list();
            return ResponseEntity.ok(timeSlot);
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
