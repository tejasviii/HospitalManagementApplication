package com.geekster.HospitalManagementApplication.Service;

import com.geekster.HospitalManagementApplication.Model.City;
import com.geekster.HospitalManagementApplication.Model.Patient;
import com.geekster.HospitalManagementApplication.Repository.IDoctorRepo;
import com.geekster.HospitalManagementApplication.Repository.IPatientRepo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PatientService {

    @Autowired
    private IPatientRepo patientRepo;

    @Autowired
    private IDoctorRepo doctorRepo;

    public ResponseEntity<String> addPatient(Patient patient) {
        String email = patient.getEmail();
        String phoneNumber = patient.getPhoneNumber();
        Patient patient1 = patientRepo.findByEmail(email);

        Patient patient2 = patientRepo.findByPhoneNumber(phoneNumber);
        if(patient1 != null || patient2 != null){
            return ResponseEntity.badRequest().body("Email or phone number already present");
        }

        patientRepo.save(patient);
        return ResponseEntity.ok("Patient added successfully");
    }

    public String suggestDoctor(Long patientId) {
        Patient patient = patientRepo.findById(patientId).orElse(null);
        if (patient == null) {
            return "Patient not found";
        }

        String cityName = patient.getCity();
        if (!isCityExist(cityName)) {
            return "We are still waiting to expand your location";
        }

        String symptom = patient.getSymptom().toLowerCase();
        Map<String, String> specialities = new HashMap<>();
        specialities.put("ear", "ENT");
        specialities.put("nose", "ENT");
        specialities.put("throat", "ENT");
        specialities.put("women reproductive", "Gynaecology");
        specialities.put("dysmenorrhea", "Gynaecology");
        specialities.put("pelvic", "Gynaecology");
        specialities.put("skin", "Dermatology");
        specialities.put("face", "Dermatology");
        specialities.put("beauty", "Dermatology");
        specialities.put("bones", "Orthopedic");

        System.out.println(specialities);
        for (Map.Entry<String, String> entry : specialities.entrySet()) {
            String symptoms = entry.getKey();
            String speciality = entry.getValue();

            if (StringUtils.containsIgnoreCase(symptom, symptoms)) {
                List<String> doctors = doctorRepo.findBySpeciality(speciality);
                if (doctors.isEmpty()) {
                    return "No doctor available for " + speciality;
                }
                return "Suggested doctor(s) for " + speciality + ": " + doctors;
            }
        }

        return "There isn't any doctor present at your location for your symptom";
    }

    public static boolean isCityExist(String cityName){
        for(City city: City.values()){
            if (city.name().equalsIgnoreCase(cityName)) {
                return true;
            }
        }
        return false;
    }
}
