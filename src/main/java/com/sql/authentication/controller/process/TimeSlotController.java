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
    @PostMapping("store/{email}")
    public ResponseEntity<?> store(@RequestBody TimeSlotDto dto,@PathVariable String email){
        try {
            TimeSlot timeSlot=timeSlotService.store(dto,email);
            return ResponseEntity.ok(timeSlot);
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
    @GetMapping("today/{email}")
    public ResponseEntity<?> currentTimeSlot(@PathVariable String email){
        try {
            TimeSlot timeSlot=timeSlotService.currentTimeSlot(email);
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
