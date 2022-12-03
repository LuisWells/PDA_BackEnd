package com.duberlyguarnizo.designartifacts.repository;

import com.duberlyguarnizo.designartifacts.model.GraphDefinition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface GraphRepository extends JpaRepository<GraphDefinition, Long> {
    List<GraphDefinition> findByName(String graphName);

    List<GraphDefinition> findByCreationAdmin(String adminName);

    List<GraphDefinition> findByUpdateAdmin(String adminName);
}
