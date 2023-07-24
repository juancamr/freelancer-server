package com.jmatch.repositories;

import com.jmatch.models.Freelancer;
import com.jmatch.models.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {

    @Query(value = "SELECT p FROM Portfolio p WHERE p.freelancer.id = :idFreelancer")
    Optional<Portfolio> getPortfolioByIdFreelancer(@Param("idFreelancer") int idFreelancer);
}
