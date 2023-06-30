package com.geekster.HospitalManagementApplication.Controller;

import com.geekster.HospitalManagementApplication.Model.Doctor;
import com.geekster.HospitalManagementApplication.Service.DoctorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @PostMapping
    public ResponseEntity<String> addDoctor(@Valid @RequestBody Doctor doctor){
        return doctorService.addDoctor(doctor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDoctor(@PathVariable Integer id){
        return doctorService.deleteDoctor(id);
    }
}