package com.duberlyguarnizo.designartifacts.repository;

import com.duberlyguarnizo.designartifacts.model.Graph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GraphRepository extends JpaRepository<Graph, Long> {
    public Graph getGraphByName(String name);
    public List<Graph> getGraphsByCreationAdmin_Name(String name);
    public List<Graph> getGraphsByUpdateAdmin_Name(String name);
    public List<Graph> findGraphsByCreationAdminName(String name);
}
