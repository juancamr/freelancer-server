package com.jmatch.repositories;

import com.jmatch.models.Project;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {

  String query1 = "" + "SELECT p.* FROM proyecto p";

  @Query(value = query1, nativeQuery = true)
  List<Project> findAll();


  String query2 = "" + "SELECT p.* FROM proyecto p WHERE p.id = :id";

  @Query(value = query2, nativeQuery = true)
  Optional<Project> findById(@Param(value = "id") long id);
}
