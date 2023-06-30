package com.geekster.HospitalManagementApplication.Service;

import com.geekster.HospitalManagementApplication.Model.Doctor;
import com.geekster.HospitalManagementApplication.Repository.IDoctorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class DoctorService {

    @Autowired
    private IDoctorRepo doctorRepo;

    public ResponseEntity<String> addDoctor(Doctor doctor) {
        String email = doctor.getEmail();
        String phoneNumber = doctor.getPhone();
        Doctor doctor1 = doctorRepo.findByEmail(email);

        Doctor doctor2 = doctorRepo.findByPhone(phoneNumber);
        if(doctor1 != null || doctor2 != null){
            return ResponseEntity.badRequest().body("Email or phone number already present");
        }

        doctorRepo.save(doctor);
        return ResponseEntity.ok("Doctor added successfully");
    }

    public ResponseEntity<String> deleteDoctor(Integer id) {
        if(!doctorRepo.existsById(id)){
            return new ResponseEntity<String>("No doctor with this id to delete", HttpStatus.NO_CONTENT);
        }

        doctorRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
