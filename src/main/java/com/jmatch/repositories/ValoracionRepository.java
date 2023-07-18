package com.jmatch.repositories;

import com.jmatch.models.Valoracion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ValoracionRepository extends JpaRepository<Valoracion, Long> {
    @Query(value="SELECT v FROM Valoracion v WHERE v.freelancer.id = :id")
    List<Valoracion> obtenerValoracionesByFreelancer(@Param("id") int id);
}
