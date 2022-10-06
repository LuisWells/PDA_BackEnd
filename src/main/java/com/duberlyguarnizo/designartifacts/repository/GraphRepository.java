package com.duberlyguarnizo.designartifacts.repository;

import com.duberlyguarnizo.designartifacts.model.Graph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GraphRepository extends JpaRepository<Graph, Long> {
    Graph findByName(String graphName);

    List<Graph> findByCreationAdmin_AdminId(Long adminId);

    List<Graph> findByUpdateAdmin_AdminId(Long adminId);
}
