package com.idea.psychiatry.modules.doctor.repository;

import com.idea.psychiatry.modules.doctor.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DoctorRepository extends JpaRepository<Doctor, UUID> {

    Optional<Doctor> findByUserId(UUID userId);

    List<Doctor> findByActiveTrue();
}
