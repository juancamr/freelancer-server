package com.jmatch.repositories;

import com.jmatch.models.Freelancer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

interface ProjectionFreelancer {
    String getNombre();

    String getUsername();

    String getCorreo();
}

public interface FreelancerRepository extends JpaRepository<Freelancer, Long> {
    Optional<Freelancer> findByUsername(String username);

    @Query("SELECT f FROM Freelancer f WHERE f.correo = :correo")
    Optional<Freelancer> findByEmail(@Param("correo") String correo);

    @Query("SELECT f FROM Freelancer f")
    List<ProjectionFreelancer> findAllForUsers();

}
