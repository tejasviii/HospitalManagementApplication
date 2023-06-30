package com.geekster.HospitalManagementApplication.Repository;

import com.geekster.HospitalManagementApplication.Model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPatientRepo extends JpaRepository<Patient,Long> {
    Patient findByEmail(String email);

    Patient findByPhoneNumber(String phoneNumber);
}
